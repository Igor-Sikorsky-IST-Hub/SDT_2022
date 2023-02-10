package com.ia01.yhnitii.shell.zip.repository.entity;

import com.ia01.yhnitii.shell.common.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
public class CachedZip extends BaseEntity {

	@NotBlank
	String filename;

	@NotBlank
	String path;

	@NotNull
	byte[] content;

	@NotNull
	LocalDateTime createdDate;

}
