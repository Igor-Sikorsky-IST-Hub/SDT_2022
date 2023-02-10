package com.ia01.yhnitii.shell.filesystem.controller.facade;

import com.ia01.yhnitii.shell.filesystem.controller.dto.TransferDto;
import com.ia01.yhnitii.shell.filesystem.controller.dto.FileDto;
import com.ia01.yhnitii.shell.filesystem.controller.dto.PathsDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileSystemFacade {

	List<FileDto> getFolder(String path);

	void deleteByPaths(PathsDto pathsDto);

	void copyFiles(TransferDto transferDto);

	void transferFiles(TransferDto transferDto);

	FileDto download(String path);

	List<FileDto> createFolders(PathsDto path);

	List<FileDto> findFiles(String path, String search);

	void uploadFile(String path, MultipartFile file);

}
