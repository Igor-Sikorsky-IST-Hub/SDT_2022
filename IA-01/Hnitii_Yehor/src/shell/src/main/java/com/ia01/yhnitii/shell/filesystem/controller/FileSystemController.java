package com.ia01.yhnitii.shell.filesystem.controller;

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

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@Tag(name = "file-system")
@Validated
@RestController
@RequestMapping("/api/v1/public/file-system")
@FieldDefaults(makeFinal = true, level = PRIVATE)
@RequiredArgsConstructor
public class FileSystemController {

	FileSystemFacade fileSystemFacade;

	@GetMapping("/folders")
	public ResponseEntity<FileDto> listFiles(@RequestParam @NotBlank String path) {
		FileDto response = fileSystemFacade.getFolder(path);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/folders")
	public ResponseEntity<FileDto> createFolder(@RequestParam @NotBlank String path) {
		FileDto response = fileSystemFacade.createFolder(path);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/files")
	public ResponseEntity<Void> deleteByPath(@RequestBody @Valid PathsDto pathsDto) {
		fileSystemFacade.deleteByPaths(pathsDto);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/files/copy")
	public ResponseEntity<Void> copyFiles(@RequestBody @Valid TransferDto transferDto) {
		fileSystemFacade.copyFiles(transferDto);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/files/transfer")
	public ResponseEntity<Void> transferFiles(@RequestBody @Valid TransferDto transferDto) {
		fileSystemFacade.transferFiles(transferDto);
		return ResponseEntity.noContent().build();
	}

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
				.body(file.getContent());
	}

}
