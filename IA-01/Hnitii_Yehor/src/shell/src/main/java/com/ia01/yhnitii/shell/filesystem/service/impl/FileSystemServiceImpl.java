package com.ia01.yhnitii.shell.filesystem.service.impl;

import com.ia01.yhnitii.shell.common.exception.BusinessException;
import com.ia01.yhnitii.shell.filesystem.service.FileSystemService;
import com.ia01.yhnitii.shell.filesystem.service.domain.FileState;
import com.ia01.yhnitii.shell.filesystem.service.domain.StatedFile;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.ia01.yhnitii.shell.common.util.constants.Constants.FileSystem.*;
import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class FileSystemServiceImpl implements FileSystemService {

	long MAX_DIRECTORY_BYTES_SIZE_TO_ZIP = 1_000_000_000L; // 1 GB

	@Override
	public File getFolder(String path) {
		File file = new File(path);

		if (!file.isDirectory()) {
			throw new BusinessException(PATH_IS_NOT_FOLDER);
		}

		return file;
	}

	@Override
	public void deleteByPath(String path) {
		try {
			FileSystemUtils.deleteRecursively(Path.of(path));
		} catch (IOException e) {
			throw new BusinessException(String.format(DELETION_FAILED, path));
		}
	}

	@Override
	public void copyFromTo(String path, String destinationPath) {
		try {
			FileSystemUtils.copyRecursively(Path.of(path), Path.of(destinationPath));
		} catch (IOException e) {
			throw new BusinessException(String.format(COPY_FAILED, path, destinationPath));
		}
	}

	@Override
	public void transferTo(String path, String destinationPath) {
		copyFromTo(path, destinationPath);
		deleteByPath(path);
	}

	@Override
	public byte[] getFileContent(File file) {
		try {
			return FileUtils.readFileToByteArray(file);
		} catch (IOException e) {
			throw new BusinessException(DOWNLOAD_FAILED);
		}
	}

	@Override
	public byte[] getFolderZippedContent(String path) {
		StatedFile statedFile = new StatedFile(path, FileState.States.ZIPPING_STARTED);

		throwIfDirectorySizeHighForZip(statedFile);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try (ZipOutputStream zippedOut = new ZipOutputStream(baos)) {
			statedFile.logState();
			zipFile(statedFile, statedFile.getName(), zippedOut);
			baos.close();
		} catch (Exception e) {
			throw new BusinessException(ZIP_FAILED);
		}

		statedFile.logState();

		return baos.toByteArray();
	}

	@Override
	public File createFolder(String path) {
		File folder = new File(path);

		if (!folder.exists()) {
			folder.mkdirs();
		}

		return folder;
	}

	private void throwIfDirectorySizeHighForZip(File directory) {
		long bytesSizeOfDirectory = FileUtils.sizeOfDirectory(directory);

		if (bytesSizeOfDirectory <= 0 || bytesSizeOfDirectory > MAX_DIRECTORY_BYTES_SIZE_TO_ZIP) {
			throw new BusinessException(String.format(
					DIRECTORY_SIZE_HIGH_TO_ZIP, MAX_DIRECTORY_BYTES_SIZE_TO_ZIP / 1_000_000_000L
			));
		}
	}

	private static <T extends File> void zipFile(T fileToZip, String fileName, ZipOutputStream zippedOut) throws IOException {
		if (fileToZip.isHidden()) {
			return;
		}

		if (fileToZip.isDirectory()) {
			String newFileName = fileName.endsWith("/") ? fileName : fileName.concat("/");

			zippedOut.putNextEntry(new ZipEntry(newFileName));
			zippedOut.closeEntry();

			File[] children = fileToZip.listFiles();
			if (children == null) return;

			for (File childFile : children) {
				zipFile(childFile, fileName + "/" + childFile.getName(), zippedOut);
			}
			return;
		}

		FileSystemResource resource = new FileSystemResource(fileToZip);

		ZipEntry zipEntry = new ZipEntry(fileName);
		zippedOut.putNextEntry(zipEntry);

		StreamUtils.copy(resource.getInputStream(), zippedOut);
	}

}
