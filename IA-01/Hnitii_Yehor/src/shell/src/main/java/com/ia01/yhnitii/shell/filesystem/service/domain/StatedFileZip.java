package com.ia01.yhnitii.shell.filesystem.service.domain;

import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.File;

import static lombok.AccessLevel.PRIVATE;

@Setter
@FieldDefaults(level = PRIVATE)
public class StatedFileZip extends File {

	FileZipState state;

	public void logState() {
		this.state.log(this);
	}

	public StatedFileZip(String pathname, FileZipState state) {
		super(pathname);
		this.state = state;
	}

}
