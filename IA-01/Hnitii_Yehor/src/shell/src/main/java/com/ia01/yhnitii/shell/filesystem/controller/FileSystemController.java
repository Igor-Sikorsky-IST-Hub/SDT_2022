package com.ia01.yhnitii.shell.filesystem.controller;

import com.ia01.yhnitii.shell.common.logging.LogExecutionTime;
import com.ia01.yhnitii.shell.filesystem.controller.dto.FileDto;
import com.ia01.yhnitii.shell.filesystem.controller.dto.PathsDto;
import com.ia01.yhnitii.shell.filesystem.controller.dto.TransferDto;
import com.ia01.yhnitii.shell.filesystem.controller.facade.FileSystemFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@Tag(name = "file-system")
@Validated
@RestController
@RequestMapping("/api/v1/public/file-system")
@FieldDefaults(makeFinal = true, level = PRIVATE)
@RequiredArgsConstructor
public class FileSystemController {

	FileSystemFacade fileSystemFacade;

	@LogExecutionTime
	@GetMapping("/folders")
	public ResponseEntity<List<FileDto>> listFiles(@RequestParam @NotBlank String path) {
		List<FileDto> response = fileSystemFacade.getFolder(path);
		return ResponseEntity.ok(response);
	}

	@LogExecutionTime
	@GetMapping("/files/search")
	public ResponseEntity<List<FileDto>> listFiles(@RequestParam @NotBlank String path,
	                                               @RequestParam @NotBlank String search) {
		List<FileDto> response = fileSystemFacade.findFiles(path, search);
		return ResponseEntity.ok(response);
	}

	@LogExecutionTime
	@PostMapping("/folders")
	public ResponseEntity<List<FileDto>> createFolder(@RequestBody @Valid PathsDto paths) {
		List<FileDto> response = fileSystemFacade.createFolders(paths);
		return ResponseEntity.ok(response);
	}

	@LogExecutionTime
	@PostMapping("/files")
	public ResponseEntity<Void> deleteByPath(@RequestPart("path") String path,
	                                         @RequestPart("file") MultipartFile file) {
		fileSystemFacade.uploadFile(path, file);
		return ResponseEntity.noContent().build();
	}

	@LogExecutionTime
	@DeleteMapping("/files")
	public ResponseEntity<Void> deleteByPath(@RequestBody @Valid PathsDto pathsDto) {
		fileSystemFacade.deleteByPaths(pathsDto);
		return ResponseEntity.noContent().build();
	}

	@LogExecutionTime
	@PutMapping("/files/copy")
	public ResponseEntity<Void> copyFiles(@RequestBody @Valid TransferDto transferDto) {
		fileSystemFacade.copyFiles(transferDto);
		return ResponseEntity.noContent().build();
	}

	@LogExecutionTime
	@PutMapping("/files/transfer")
	public ResponseEntity<Void> transferFiles(@RequestBody @Valid TransferDto transferDto) {
		fileSystemFacade.transferFiles(transferDto);
		return ResponseEntity.noContent().build();
	}

	@LogExecutionTime
	@GetMapping("/files")
	public ResponseEntity<byte[]> download(@RequestParam @NotBlank String path,
	                                       @RequestParam(defaultValue = "true") Boolean isAttachment) {
		FileDto file = fileSystemFacade.download(path);

		String contentDisposition = String.format(
				"%s; filename=\"%s\"",
				isAttachment ? "attachment" : "inline",
				file.getTitle()
		);

		return ResponseEntity.ok()
				.header(CONTENT_DISPOSITION, contentDisposition)
				.header(ACCESS_CONTROL_EXPOSE_HEADERS, CONTENT_DISPOSITION)
				.body(file.getContent());
	}

}
