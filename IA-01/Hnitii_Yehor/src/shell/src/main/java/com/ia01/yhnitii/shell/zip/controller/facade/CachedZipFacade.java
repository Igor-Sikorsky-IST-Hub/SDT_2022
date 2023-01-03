package com.ia01.yhnitii.shell.zip.controller.facade;

import com.ia01.yhnitii.shell.zip.controller.dto.CachedZipDto;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface CachedZipFacade {

	List<CachedZipDto> findAll();

	void deleteByIdIn(Set<UUID> ids);

	CachedZipDto downloadById(UUID id);

}
