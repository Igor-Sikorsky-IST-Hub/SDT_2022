package com.ia01.yhnitii.shell.zip.controller;

import com.ia01.yhnitii.shell.common.dto.IdsDto;
import com.ia01.yhnitii.shell.common.logging.LogExecutionTime;
import com.ia01.yhnitii.shell.zip.controller.dto.CachedZipDto;
import com.ia01.yhnitii.shell.zip.controller.facade.CachedZipFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@Tag(name = "cached-zip")
@RestController
@RequestMapping("/api/v1/public/zips")
@FieldDefaults(makeFinal = true, level = PRIVATE)
@RequiredArgsConstructor
public class CachedZipController {

	CachedZipFacade cachedZipFacade;

	@LogExecutionTime
	@GetMapping
	public ResponseEntity<List<CachedZipDto>> findAll() {
		List<CachedZipDto> cachedZipDtoList = cachedZipFacade.findAll();
		return ResponseEntity.ok(cachedZipDtoList);
	}

	@LogExecutionTime
	@GetMapping("/{id}")
	public ResponseEntity<byte[]> download(@PathVariable UUID id,
	                                       @RequestParam(defaultValue = "true") Boolean isAttachment) {
		CachedZipDto cachedZipDto = cachedZipFacade.downloadById(id);

		String contentDisposition = String.format(
				"%s; filename=\"%s\"",
				isAttachment ? "attachment" : "inline",
				cachedZipDto.getFilename()
		);

		return ResponseEntity.ok()
				.header(CONTENT_DISPOSITION, contentDisposition)
				.header(ACCESS_CONTROL_EXPOSE_HEADERS, CONTENT_DISPOSITION)
				.body(cachedZipDto.getContent());
	}

	@LogExecutionTime
	@DeleteMapping
	public ResponseEntity<Void> deleteByIds(@RequestBody @Valid IdsDto ids) {
		cachedZipFacade.deleteByIdIn(ids.getIds());
		return ResponseEntity.noContent().build();
	}

}