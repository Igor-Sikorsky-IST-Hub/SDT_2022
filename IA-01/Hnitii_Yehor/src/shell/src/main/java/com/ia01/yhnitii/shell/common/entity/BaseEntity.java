package com.ia01.yhnitii.shell.common.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;


@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = PROTECTED)
@EqualsAndHashCode(of = {"id"})
@SuperBuilder(toBuilder = true)
public class BaseEntity implements Serializable {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
			name = "UUID",
			strategy = "org.hibernate.id.UUIDGenerator"
	)
	@Column(name = "id", updatable = false, nullable = false)
	UUID id;

}