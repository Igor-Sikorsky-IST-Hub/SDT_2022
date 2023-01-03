package com.ia01.yhnitii.shell.filesystem.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface FileSystemService {

	File getFolder(String path);

	void deleteByPath(String path);

	void copyFromTo(String path, String destinationPath);

	void transferTo(String path, String destinationPath);

	byte[] getFileContent(File file);

	byte[] getFolderZippedContent(String path);

	File createFolder(String path);

	List<File> findFiles(String path, String search);

	File addFile(String path, MultipartFile file);

}
