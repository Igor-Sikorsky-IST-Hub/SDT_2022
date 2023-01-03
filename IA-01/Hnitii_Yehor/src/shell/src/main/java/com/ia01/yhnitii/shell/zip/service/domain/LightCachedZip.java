package com.ia01.yhnitii.shell.zip.service.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LightCachedZip {

	UUID id;

	String filename;

	String path;

	LocalDateTime createdDate;

}
