package com.ia01.yhnitii.shell.zip.repository;

import com.ia01.yhnitii.shell.zip.repository.entity.CachedZip;
import com.ia01.yhnitii.shell.zip.service.domain.LightCachedZip;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CachedZipRepository extends JpaRepository<CachedZip, UUID> {

	@Query("""
			select new com.ia01.yhnitii.shell.zip.service.domain.LightCachedZip(id,filename,path,createdDate)
			from CachedZip
			""")
	List<LightCachedZip> findAllLight(Sort sort);

	Optional<CachedZip> findByPath(String path);

	void deleteAllByCreatedDateLessThanEqual(LocalDateTime dateTime);

}
