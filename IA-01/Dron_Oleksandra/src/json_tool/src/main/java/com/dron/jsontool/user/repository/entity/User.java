package com.dron.jsontool.user.repository.entity;

import com.dron.jsontool.config.entity.BaseEntity;
import com.dron.jsontool.jsonshema.repositiry.entity.JsonSchema;
import com.dron.jsontool.user.repository.entity.enumeration.Role;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
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
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	Role role = Role.ROLE_USER;

}
