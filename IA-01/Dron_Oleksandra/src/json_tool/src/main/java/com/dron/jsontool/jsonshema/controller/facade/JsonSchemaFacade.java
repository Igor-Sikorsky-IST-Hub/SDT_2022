package com.dron.jsontool.jsonshema.controller.facade;

import com.dron.jsontool.jsonshema.controller.dto.JsonSchemaDto;

import java.util.UUID;

public interface JsonSchemaFacade {

	JsonSchemaDto save(JsonSchemaDto jsonSchemaDto);

	JsonSchemaDto findLast();

	JsonSchemaDto findPrevious(UUID id);

	JsonSchemaDto findNext(UUID id);

}
