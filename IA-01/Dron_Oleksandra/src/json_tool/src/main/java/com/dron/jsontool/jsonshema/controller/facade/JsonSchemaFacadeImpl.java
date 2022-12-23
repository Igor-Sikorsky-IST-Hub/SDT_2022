package com.dron.jsontool.jsonshema.controller.facade;

import com.dron.jsontool.config.exception.BusinessException;
import com.dron.jsontool.config.logger.Logger;
import com.dron.jsontool.config.security.service.SecurityService;
import com.dron.jsontool.jsonhistory.repository.entity.JsonHistory;
import com.dron.jsontool.jsonhistory.service.JsonHistoryService;
import com.dron.jsontool.jsonshema.controller.dto.JsonSchemaDto;
import com.dron.jsontool.jsonshema.controller.mapper.JsonSchemaMapper;
import com.dron.jsontool.jsonshema.repositiry.entity.JsonSchema;
import com.dron.jsontool.jsonshema.service.JsonSchemaService;
import com.dron.jsontool.user.repository.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.dron.jsontool.config.utils.Constants.JsonSchema.NEXT_JSON_SCHEMA_NOT_FOUND;
import static com.dron.jsontool.config.utils.Constants.JsonSchema.PREV_JSON_SCHEMA_NOT_FOUND;
import static lombok.AccessLevel.PRIVATE;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class JsonSchemaFacadeImpl implements JsonSchemaFacade {

	JsonSchemaService jsonSchemaService;
	JsonHistoryService jsonHistoryService;
	SecurityService securityService;

	Logger logger = Logger.getInstance();

	JsonSchemaMapper jsonSchemaMapper;

	@Override
	@Transactional
	public JsonSchemaDto save(JsonSchemaDto jsonSchemaDto) {
		User authUser = securityService.getAuthUser();

		JsonSchema jsonSchema = jsonSchemaMapper.toEntity(jsonSchemaDto);
		jsonSchema.setOwner(authUser);

		JsonSchema saved = jsonSchemaService.save(jsonSchema);

		if (jsonSchemaDto.getPreviousId() != null) {
			saveHistory(jsonSchemaDto.getPreviousId(), saved);
		}

		logger.info("User: {}, saved schema: {}, title: {}",
				authUser.getId(), saved.getId(), saved.getTitle()
		);

		return jsonSchemaMapper.toDto(saved);
	}

	@Override
	public JsonSchemaDto findLast() {
		User authUser = securityService.getAuthUser();
		JsonSchema jsonSchema = jsonSchemaService.findLastByUserId(authUser.getId());
		return jsonSchemaMapper.toDto(jsonSchema);
	}

	@Override
	@Transactional(readOnly = true)
	public JsonSchemaDto findPrevious(UUID id) {
		JsonSchema jsonSchema = jsonSchemaService.findById(id);

		JsonSchema.SchemaIterator iterator = jsonSchema.iterator();

		if (iterator.hasPrevious()) {
			return jsonSchemaMapper.toDto(iterator.previous());
		} else
			throw new BusinessException(PREV_JSON_SCHEMA_NOT_FOUND);
	}

	@Override
	public JsonSchemaDto findNext(UUID id) {
		JsonSchema jsonSchema = jsonSchemaService.findById(id);

		JsonSchema.SchemaIterator iterator = jsonSchema.iterator();

		if (iterator.hasNext()) {
			return jsonSchemaMapper.toDto(iterator.next());
		} else
			throw new BusinessException(NEXT_JSON_SCHEMA_NOT_FOUND);
	}

	private void saveHistory(UUID previousSchemaId, JsonSchema nextSchema) {
		JsonSchema previousSchema = jsonSchemaService.findById(previousSchemaId);
		JsonHistory history = JsonHistory.getBuilder()
				.next(nextSchema)
				.previous(previousSchema)
				.build();

		JsonHistory savedHistory = jsonHistoryService.save(history);

		nextSchema.setPreviousHistory(savedHistory);
		previousSchema.setNextHistory(savedHistory);
	}

}
