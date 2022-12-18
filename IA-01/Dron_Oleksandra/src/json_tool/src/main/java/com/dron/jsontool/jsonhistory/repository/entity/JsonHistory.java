package com.dron.jsontool.jsonhistory.repository.entity;

import com.dron.jsontool.config.entity.BaseEntity;
import com.dron.jsontool.jsonshema.repositiry.entity.JsonSchema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class JsonHistory extends BaseEntity {

	@OneToOne(optional = false, fetch = FetchType.LAZY)
	JsonSchema previous;

	@OneToOne(optional = false, fetch = FetchType.LAZY)
	JsonSchema next;

	public static JsonSchemaBuilder getBuilder() {
		return new JsonSchemaBuilder();
	}

	private JsonHistory(JsonSchemaBuilder builder) {
		this.previous = builder.previous;
		this.next = builder.next;
	}

	@FieldDefaults(level = PRIVATE)
	public static class JsonSchemaBuilder {
		JsonSchema previous;
		JsonSchema next;

		public JsonSchemaBuilder previous(JsonSchema previous) {
			this.previous = previous;
			return this;
		}

		public JsonSchemaBuilder next(JsonSchema next) {
			this.next = next;
			return this;
		}

		public JsonHistory build() {
			return new JsonHistory(this);
		}

	}

}
