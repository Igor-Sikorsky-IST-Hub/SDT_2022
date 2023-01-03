package com.ia01.yhnitii.shell.filesystem.controller.facade.impl;

import com.ia01.yhnitii.shell.common.exception.BusinessException;
import com.ia01.yhnitii.shell.filesystem.controller.dto.FileDto;
import com.ia01.yhnitii.shell.filesystem.controller.dto.PathsDto;
import com.ia01.yhnitii.shell.filesystem.controller.dto.TransferDto;
import com.ia01.yhnitii.shell.filesystem.controller.facade.FileSystemFacade;
import com.ia01.yhnitii.shell.filesystem.controller.mapper.FileSystemMapper;
import com.ia01.yhnitii.shell.filesystem.service.FileSystemService;
import com.ia01.yhnitii.shell.history.repository.entity.History;
import com.ia01.yhnitii.shell.history.repository.entity.enumeration.Action;
import com.ia01.yhnitii.shell.history.service.HistoryService;
import com.ia01.yhnitii.shell.zip.repository.entity.CachedZip;
import com.ia01.yhnitii.shell.zip.service.CachedZipService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.ia01.yhnitii.shell.common.util.constants.Constants.History.ILLEGAL_ARGUMENTS;
import static lombok.AccessLevel.PRIVATE;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class FileSystemFacadeImpl implements FileSystemFacade {

	FileSystemService fileSystemService;
	CachedZipService cachedZipService;
	HistoryService historyService;

	FileSystemMapper fileSystemMapper;

	@Override
	public List<FileDto> getFolder(String path) {
		File file = fileSystemService.getFolder(path);
		return Arrays.stream(Objects.requireNonNull(file.listFiles())).map(fileSystemMapper::toDto).toList();
	}

	@Override
	public void deleteByPaths(PathsDto pathsDto) {
		pathsDto.getPaths().forEach(path -> {
			fileSystemService.deleteByPath(path);
			historyService.save(historyFactory(path, null, Action.DELETE));
		});
	}

	@Override
	public void copyFiles(TransferDto transferDto) {
		transferDto.getPaths()
				.forEach(path -> {
					String destinationPath = transferDto.getDestinationPath();
					fileSystemService.copyFromTo(path, destinationPath);
					historyService.save(historyFactory(path, destinationPath, Action.COPY));
				});
	}

	@Override
	public void transferFiles(TransferDto transferDto) {
		transferDto.getPaths()
				.forEach(path -> {
					String destinationPath = transferDto.getDestinationPath();
					fileSystemService.transferTo(path, destinationPath);
					historyService.save(historyFactory(path, destinationPath, Action.TRANSFER));
				});
	}

	@Override
	public FileDto download(String path) {
		File file = new File(path);

		byte[] content;

		if (file.isDirectory()) {
			content = zipFolderOrGetFromCache(file);
		} else {
			content = fileSystemService.getFileContent(file);
		}

		return fileSystemMapper.toDto(file)
				.toBuilder()
				.title(file.isDirectory() ? file.getName() + ".zip" : file.getName())
				.content(content)
				.build();
	}

	@Override
	public List<FileDto> createFolders(PathsDto paths) {
		return paths.getPaths()
				.stream()
				.map(fileSystemService::createFolder)
				.peek(folder -> historyService.save(historyFactory(
						folder.getPath(), null, Action.NEW_FOLDER)
				))
				.map(fileSystemMapper::toDto)
				.toList();
	}

	@Override
	public List<FileDto> findFiles(String path, String search) {
		return fileSystemService
				.findFiles(path, search)
				.stream()
				.map(fileSystemMapper::toDto)
				.toList();
	}

	@Override
	public void uploadFile(String path, MultipartFile file) {
		File newFile = fileSystemService.addFile(path, file);
		historyService.save(historyFactory(newFile.getPath(), null, Action.NEW_FILE));
	}

	private byte[] zipFolderOrGetFromCache(File file) {
		return cachedZipService
				.findOptByPath(file.getPath())
				.map(CachedZip::getContent)
				.orElseGet(() -> {
					byte[] folderZippedContent = fileSystemService.getFolderZippedContent(file.getPath());

					cachedZipService.save(
							CachedZip.builder()
									.filename(file.getName() + ".zip")
									.createdDate(LocalDateTime.now())
									.content(folderZippedContent)
									.path(file.getPath())
									.build()
					);

					return folderZippedContent;
				});
	}

	private History historyFactory(@NotNull String pathFrom, String pathTo, @NotNull Action action) {
		if (action.requireTwoPaths()) {
			throwIfAnyBlank(pathFrom, pathTo);
		} else {
			throwIfAnyBlank(pathFrom);
		}

		History.HistBuilder builder = History.newBuilder();

		builder = switch (action) {
			case COPY -> builder.history(String.format("Copy %s to: \n%s", pathFrom, pathTo));
			case TRANSFER -> builder.history(String.format("Transfer %s to: \n%s", pathFrom, pathTo));
			case DELETE -> builder.history(String.format("Delete path: %s", pathFrom));
			case NEW_FILE -> builder.history(String.format("New file uploaded: %s", pathFrom));
			case NEW_FOLDER -> builder.history(String.format("New folder created: %s", pathFrom));
		};

		return builder
				.action(action)
				.build();
	}

	private void throwIfAnyBlank(String... args) {
		if (StringUtils.isAnyBlank(args)) {
			throw new BusinessException(ILLEGAL_ARGUMENTS);
		}
	}

}
