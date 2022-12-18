package com.dron.jsontool.jsonshema.repositiry.entity;

import com.dron.jsontool.config.entity.BaseEntity;
import com.dron.jsontool.jsonhistory.repository.entity.JsonHistory;
import com.dron.jsontool.user.repository.entity.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Iterator;

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
	String title;

	@Column
	String description;

	@EqualsAndHashCode.Exclude
	@ManyToOne(optional = false)
	User owner;

	@OneToOne(mappedBy = "previous", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	JsonHistory nextHistory;

	@OneToOne(mappedBy = "next", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	JsonHistory previousHistory;

	@Column(nullable = false)
	@Builder.Default
	LocalDateTime createdDate = LocalDateTime.now();

	public SchemaIterator iterator() {
		return new SchemaIterator();
	}

	public class SchemaIterator implements Iterator<JsonSchema> {
		@Override
		public boolean hasNext() {
			return nextHistory != null && nextHistory.getNext() != null;
		}

		@Override
		public JsonSchema next() {
			return nextHistory.getNext();
		}

		public boolean hasPrevious() {
			return previousHistory != null && previousHistory.getPrevious() != null;
		}

		public JsonSchema previous() {
			return previousHistory.getPrevious();
		}
	}

}
