package com.ia01.yhnitii.shell.filesystem.controller.facade;

import com.ia01.yhnitii.shell.filesystem.controller.dto.TransferDto;
import com.ia01.yhnitii.shell.filesystem.controller.dto.FileDto;
import com.ia01.yhnitii.shell.filesystem.controller.dto.PathsDto;

public interface FileSystemFacade {

	FileDto getFolder(String path);

	void deleteByPaths(PathsDto pathsDto);

	void copyFiles(TransferDto transferDto);

	void transferFiles(TransferDto transferDto);

	FileDto download(String path);

	FileDto createFolder(String path);

}
