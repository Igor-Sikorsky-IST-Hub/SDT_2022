package com.ia01.yhnitii.shell.command.repository.entity;

import com.ia01.yhnitii.shell.common.entity.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Setter
@Entity
@NoArgsConstructor
@FieldDefaults(level = PROTECTED)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
public class Command extends BaseEntity {

	@NotBlank
	@Column(nullable = false)
	String command;

	@Column(nullable = false)
	Instant executed;

}