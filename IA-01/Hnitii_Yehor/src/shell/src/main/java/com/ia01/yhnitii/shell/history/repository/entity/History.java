package com.ia01.yhnitii.shell.history.repository.entity;

import com.ia01.yhnitii.shell.common.entity.BaseEntity;
import com.ia01.yhnitii.shell.history.repository.entity.enumeration.Action;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Setter
@Entity
@NoArgsConstructor
@FieldDefaults(level = PROTECTED)
@EqualsAndHashCode(callSuper = true)
public class History extends BaseEntity {

	@NotBlank
	String history;

	LocalDateTime executed = LocalDateTime.now();

	@NotNull
	@Enumerated(EnumType.STRING)
	Action action;

	private History(HistBuilder builder) {
		this.id = builder.id;
		this.history = builder.history;
		this.action = builder.action;
		this.executed = builder.executed;
	}

	public static HistBuilder newBuilder() {
		return new HistBuilder();
	}

	public static class HistBuilder {
		private UUID id;
		private String history;
		private Action action;
		private LocalDateTime executed = LocalDateTime.now();

		public HistBuilder id(UUID id) {
			this.id = id;
			return this;
		}

		public HistBuilder history(String history) {
			this.history = history;
			return this;
		}

		public HistBuilder action(Action action) {
			this.action = action;
			return this;
		}

		public HistBuilder executed(LocalDateTime executed) {
			this.executed = executed;
			return this;
		}

		public History build() {
			return new History(this);
		}
	}

}