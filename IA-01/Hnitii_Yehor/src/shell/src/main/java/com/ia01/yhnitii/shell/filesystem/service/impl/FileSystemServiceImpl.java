package com.ia01.yhnitii.shell.filesystem.service.impl;

import com.ia01.yhnitii.shell.common.exception.BusinessException;
import com.ia01.yhnitii.shell.filesystem.service.FileSystemService;
import com.ia01.yhnitii.shell.filesystem.service.domain.FileZipState;
import com.ia01.yhnitii.shell.filesystem.service.domain.StatedFileZip;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.ia01.yhnitii.shell.common.util.constants.Constants.FileSystem.*;
import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class FileSystemServiceImpl implements FileSystemService {

	final static long MAX_DIRECTORY_BYTES_SIZE_TO_ZIP = 1_000_000_000L; // 1 GB

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
			File src = new File(path);
			File dest = new File(destinationPath + "/" + src.getName());
			if (dest.exists()) {
				throw new BusinessException(String.format(FILE_EXISTS, dest.getPath()));
			}
			FileSystemUtils.copyRecursively(src, dest);
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
		StatedFileZip statedFile = new StatedFileZip(path, FileZipState.States.ZIPPING_STARTED);

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

	@Override
	public List<File> findFiles(String path, String search) {
		try (Stream<Path> pathStream = Files.find(Paths.get(path), 6,
				(x, y) -> StringUtils.containsIgnoreCase(x.getFileName().toString(), search.trim())
		)) {
			return pathStream.map(Path::toFile).toList();
		} catch (IOException e) {
			throw new BusinessException(String.format(SEARCH_FAILED, path));
		}
	}

	@Override
	public File addFile(String path, MultipartFile file) {
		File newFile = new File(path + "/" + file.getOriginalFilename());
		try {
			file.transferTo(newFile);
		} catch (IOException e) {
			throw new BusinessException(UPLOAD_FAILED);
		}
		return newFile;
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
