package com.dron.jsontool.jsonshema.service;

import com.dron.jsontool.config.exception.NotFoundException;
import com.dron.jsontool.jsonshema.repositiry.JsonSchemaRepository;
import com.dron.jsontool.jsonshema.repositiry.entity.JsonSchema;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.dron.jsontool.config.utils.Constants.JsonSchema.JSON_SCHEMA_NOT_FOUND;
import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class JsonSchemaServiceImpl implements JsonSchemaService {

	JsonSchemaRepository jsonSchemaRepository;

	@Override
	public JsonSchema save(JsonSchema jsonSchema) {
		return jsonSchemaRepository.save(jsonSchema);
	}

	@Override
	public JsonSchema findLastByUserId(UUID ownerId) {
		return jsonSchemaRepository
				.findTopByOwnerIdOrderByCreatedDateDesc(ownerId)
				.orElseThrow(()-> new NotFoundException(JSON_SCHEMA_NOT_FOUND));
	}

	@Override
	public JsonSchema findById(UUID id) {
		return jsonSchemaRepository
				.findById(id)
				.orElseThrow(()-> new NotFoundException(JSON_SCHEMA_NOT_FOUND));
	}
}
