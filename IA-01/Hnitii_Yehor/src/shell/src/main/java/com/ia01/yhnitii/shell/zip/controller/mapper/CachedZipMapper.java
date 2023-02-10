package com.ia01.yhnitii.shell.zip.controller.mapper;

import com.ia01.yhnitii.shell.zip.controller.dto.CachedZipDto;
import com.ia01.yhnitii.shell.zip.repository.entity.CachedZip;
import com.ia01.yhnitii.shell.zip.service.domain.LightCachedZip;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CachedZipMapper {

	CachedZipDto toDto(LightCachedZip entity);

	CachedZipDto toDto(CachedZip entity);

}
