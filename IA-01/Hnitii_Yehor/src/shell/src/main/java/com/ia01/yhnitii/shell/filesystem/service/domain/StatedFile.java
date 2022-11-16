package com.ia01.yhnitii.shell.filesystem.service.domain;

import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.File;
import java.net.URI;

import static lombok.AccessLevel.PRIVATE;

@Setter
@FieldDefaults(level = PRIVATE)
public class StatedFile extends File {

	FileState state;

	public void logState() {
		this.state.log(this);
	}

	public StatedFile(String pathname, FileState state) {
		super(pathname);
		this.state = state;
	}

	public StatedFile(String parent, String child, FileState state) {
		super(parent, child);
		this.state = state;
	}

	public StatedFile(File parent, String child, FileState state) {
		super(parent, child);
		this.state = state;
	}

	public StatedFile(URI uri, FileState state) {
		super(uri);
		this.state = state;
	}

}
