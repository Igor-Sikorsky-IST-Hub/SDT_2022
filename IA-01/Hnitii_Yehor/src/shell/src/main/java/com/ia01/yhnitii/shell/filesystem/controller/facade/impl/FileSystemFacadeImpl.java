package com.ia01.yhnitii.shell.filesystem.controller.facade.impl;

import com.ia01.yhnitii.shell.filesystem.controller.dto.FileDto;
import com.ia01.yhnitii.shell.filesystem.controller.dto.PathsDto;
import com.ia01.yhnitii.shell.filesystem.controller.dto.TransferDto;
import com.ia01.yhnitii.shell.filesystem.controller.facade.FileSystemFacade;
import com.ia01.yhnitii.shell.filesystem.controller.mapper.FileSystemMapper;
import com.ia01.yhnitii.shell.filesystem.service.FileSystemService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.io.File;

import static lombok.AccessLevel.PRIVATE;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class FileSystemFacadeImpl implements FileSystemFacade {

	FileSystemService fileSystemService;

	FileSystemMapper fileSystemMapper;

	@Override
	public FileDto getFolder(String path) {
		File file = fileSystemService.getFolder(path);
		return fileSystemMapper.toDto(file);
	}

	@Override
	public void deleteByPaths(PathsDto pathsDto) {
		pathsDto.getPaths().forEach(fileSystemService::deleteByPath);
	}

	@Override
	public void copyFiles(TransferDto transferDto) {
		transferDto.getPaths()
				.forEach(path -> fileSystemService.copyFromTo(path, transferDto.getDestinationPath()));
	}

	@Override
	public void transferFiles(TransferDto transferDto) {
		transferDto.getPaths()
				.forEach(path -> fileSystemService.transferTo(path, transferDto.getDestinationPath()));
	}

	@Override
	public FileDto download(String path) {
		File file = new File(path);

		byte[] content;

		if (file.isDirectory()) {
			content = fileSystemService.getFolderZippedContent(path);
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
	public FileDto createFolder(String path) {
		File folder = fileSystemService.createFolder(path);
		return fileSystemMapper.toDto(folder);
	}

}
