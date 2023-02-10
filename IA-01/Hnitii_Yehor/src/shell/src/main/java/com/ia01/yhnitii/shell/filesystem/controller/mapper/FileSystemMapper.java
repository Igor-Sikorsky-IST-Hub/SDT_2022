package com.ia01.yhnitii.shell.filesystem.controller.mapper;

import com.ia01.yhnitii.shell.filesystem.controller.dto.FileDto;
import org.mapstruct.Mapper;

import java.io.File;

@Mapper(componentModel = "spring")
public interface FileSystemMapper {

	default FileDto toDto(File file) {
		return FileDto.builder()
				.isFolder(file.isDirectory())
				.title(file.getName())
				.path(file.getPath())
				.build();
	}

}
