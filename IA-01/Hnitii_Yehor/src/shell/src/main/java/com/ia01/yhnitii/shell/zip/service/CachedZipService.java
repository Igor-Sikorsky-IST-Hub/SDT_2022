package com.ia01.yhnitii.shell.zip.service;

import com.ia01.yhnitii.shell.zip.repository.entity.CachedZip;
import com.ia01.yhnitii.shell.zip.service.domain.LightCachedZip;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface CachedZipService {

	CachedZip save(CachedZip entity);

	List<LightCachedZip> findAll();

	CachedZip findById(UUID id);

	void deleteByIdIn(Set<UUID> ids);

	Optional<CachedZip> findOptByPath(String path);

	void deleteAllByCreatedDateBefore(LocalDateTime dateTime);

}
