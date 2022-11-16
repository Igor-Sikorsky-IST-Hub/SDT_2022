package com.ia01.yhnitii.shell.filesystem.controller.mapper;

import com.ia01.yhnitii.shell.filesystem.controller.dto.FileDto;
import org.mapstruct.Mapper;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface FileSystemMapper {

	default FileDto toDto(File file) {
		File[] fileList = file.listFiles();

		List<FileDto> files = file.isDirectory() && Objects.nonNull(fileList) ?
				Arrays.stream(fileList)
						.map(innerFile -> FileDto.builder()
								.isFolder(innerFile.isDirectory())
								.title(innerFile.getName())
								.path(innerFile.getPath())
								.build()
						).collect(Collectors.toList()) : null;

		return FileDto.builder()
				.size(file.getTotalSpace())
				.isFolder(file.isDirectory())
				.title(file.getName())
				.path(file.getPath())
				.files(files)
				.build();
	}

}
