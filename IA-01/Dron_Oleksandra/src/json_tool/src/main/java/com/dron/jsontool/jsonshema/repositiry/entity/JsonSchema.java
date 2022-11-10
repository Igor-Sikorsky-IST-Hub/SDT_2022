package com.dron.jsontool.jsonshema.repositiry.entity;

import com.dron.jsontool.common.entity.BaseEntity;
import com.dron.jsontool.jsonhistory.repository.entity.JsonHistory;
import com.dron.jsontool.user.repository.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class JsonSchema extends BaseEntity {

	@Column(nullable = false)
	String json;

	@Column(nullable = false)
	String name;

	@EqualsAndHashCode.Exclude
	@ManyToOne(optional = false)
	User owner;

	@OneToOne(mappedBy = "previous")
	JsonHistory previousHistory;

	@OneToOne(mappedBy = "current")
	JsonHistory currentHistory;

}
