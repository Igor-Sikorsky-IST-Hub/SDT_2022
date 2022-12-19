package com.dron.jsontool.jsonshema.controller;


import com.dron.jsontool.jsonshema.controller.dto.JsonSchemaDto;
import com.dron.jsontool.jsonshema.controller.facade.JsonSchemaFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Tag(name = "json-schema")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/private/json-schemas")
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class JsonSchemaController {

	JsonSchemaFacade jsonSchemaFacade;

	@PostMapping
	public ResponseEntity<JsonSchemaDto> save(@RequestBody @Valid JsonSchemaDto jsonSchemaDto) {
		JsonSchemaDto response = jsonSchemaFacade.save(jsonSchemaDto);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/last")
	public ResponseEntity<JsonSchemaDto> findLast() {
		JsonSchemaDto response = jsonSchemaFacade.findLast();
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}/previous")
	public ResponseEntity<JsonSchemaDto> findPrevious(@PathVariable UUID id) {
		JsonSchemaDto response = jsonSchemaFacade.findPrevious(id);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}/next")
	public ResponseEntity<JsonSchemaDto> findNext(@PathVariable UUID id) {
		JsonSchemaDto response = jsonSchemaFacade.findNext(id);
		return ResponseEntity.ok(response);
	}


}
