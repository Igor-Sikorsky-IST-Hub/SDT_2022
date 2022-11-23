package com.dron.jsontool.user.repository.entity;

import com.dron.jsontool.common.entity.BaseEntity;
import com.dron.jsontool.jsonshema.repositiry.entity.JsonSchema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "system_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
public class User extends BaseEntity {

	@Column(nullable = false, unique = true)
	String email;

	@Column(nullable = false)
	String password;

	@Column(nullable = false)
	String firstname;

	@Column(nullable = false)
	String lastname;

	@EqualsAndHashCode.Exclude
	@OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
	List<JsonSchema> jsonSchemas;

	@Builder.Default
	@Column(nullable = false)
	String roles = "";

	public List<String> getRoleList() {
		if (this.roles.length() > 0) {
			return Arrays.asList(this.roles.split(","));
		}
		return new ArrayList<>();
	}

}
