package com.ia01.yhnitii.shell.zip.controller.facade.impl;

import com.ia01.yhnitii.shell.zip.controller.dto.CachedZipDto;
import com.ia01.yhnitii.shell.zip.controller.facade.CachedZipFacade;
import com.ia01.yhnitii.shell.zip.controller.mapper.CachedZipMapper;
import com.ia01.yhnitii.shell.zip.repository.entity.CachedZip;
import com.ia01.yhnitii.shell.zip.service.CachedZipService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class CachedZipFacadeImpl implements CachedZipFacade {

	CachedZipService cachedZipService;
	CachedZipMapper cachedZipMapper;


	@Override
	public List<CachedZipDto> findAll() {
		return cachedZipService
				.findAll()
				.stream()
				.map(cachedZipMapper::toDto)
				.toList();
	}

	@Override
	public void deleteByIdIn(Set<UUID> ids) {
		cachedZipService.deleteByIdIn(ids);
	}

	@Override
	public CachedZipDto downloadById(UUID id) {
		CachedZip cachedZip = cachedZipService.findById(id);
		return cachedZipMapper.toDto(cachedZip);
	}
}
