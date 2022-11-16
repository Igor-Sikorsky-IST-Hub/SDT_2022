package com.ia01.yhnitii.shell.filesystem.service;

import java.io.File;

public interface FileSystemService {

	File getFolder(String path);

	void deleteByPath(String path);

	void copyFromTo(String path, String destinationPath);

	void transferTo(String path, String destinationPath);

	byte[] getFileContent(File file);

	byte[] getFolderZippedContent(String path);

	File createFolder(String path);
}
