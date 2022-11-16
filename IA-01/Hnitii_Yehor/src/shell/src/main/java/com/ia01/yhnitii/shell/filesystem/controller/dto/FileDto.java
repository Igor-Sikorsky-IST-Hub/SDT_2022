package com.ia01.yhnitii.shell.filesystem.controller.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
@SuperBuilder(toBuilder = true)
public class FileDto {

	String path;

	String title;

	long size;

	List<FileDto> files;

	byte[] content;

	@Builder.Default
	boolean isFolder = false;

}
