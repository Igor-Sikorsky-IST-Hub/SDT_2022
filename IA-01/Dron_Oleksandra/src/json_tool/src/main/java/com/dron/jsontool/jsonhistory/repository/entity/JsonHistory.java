package com.dron.jsontool.jsonhistory.repository.entity;

import com.dron.jsontool.common.entity.BaseEntity;
import com.dron.jsontool.jsonshema.repositiry.entity.JsonSchema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class JsonHistory extends BaseEntity {

	@OneToOne(optional = false)
	JsonSchema previous;

	@OneToOne(optional = false)
	JsonSchema current;

}
