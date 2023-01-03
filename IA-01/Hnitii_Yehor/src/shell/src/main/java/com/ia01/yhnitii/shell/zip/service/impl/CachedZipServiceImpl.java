package com.ia01.yhnitii.shell.zip.service.impl;

import com.ia01.yhnitii.shell.common.exception.NotFoundException;
import com.ia01.yhnitii.shell.zip.repository.CachedZipRepository;
import com.ia01.yhnitii.shell.zip.repository.entity.CachedZip;
import com.ia01.yhnitii.shell.zip.service.CachedZipService;
import com.ia01.yhnitii.shell.zip.service.domain.LightCachedZip;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static com.ia01.yhnitii.shell.common.util.constants.Constants.CachedZip.CACHED_ZIP_NOT_FOUND;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class CachedZipServiceImpl implements CachedZipService {

	CachedZipRepository cachedZipRepository;

	@Override
	@Transactional
	public CachedZip save(CachedZip entity) {
		cachedZipRepository
				.findByPath(entity.getPath())
				.ifPresent(zip -> entity.setId(zip.getId()));

		return cachedZipRepository.save(entity);
	}

	@Override
	public List<LightCachedZip> findAll() {
		return cachedZipRepository.findAllLight(Sort.by(DESC, "createdDate"));
	}

	@Override
	public CachedZip findById(UUID id) {
		return cachedZipRepository
				.findById(id)
				.orElseThrow(() -> new NotFoundException(CACHED_ZIP_NOT_FOUND));
	}

	@Override
	@Transactional
	public void deleteByIdIn(Set<UUID> ids) {
		cachedZipRepository.deleteAllById(ids);
	}

	@Override
	public Optional<CachedZip> findOptByPath(String path) {
		return cachedZipRepository.findByPath(path);
	}

	@Override
	@Transactional
	public void deleteAllByCreatedDateBefore(LocalDateTime dateTime) {
		cachedZipRepository.deleteAllByCreatedDateLessThanEqual(dateTime);
	}

}
