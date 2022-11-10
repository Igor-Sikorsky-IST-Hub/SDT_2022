package com.dron.jsontool.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {

	List<T> result;

	long count;

	public static <T> PageResponse<T> of(List<T> result, long count) {
		return new PageResponse<>(result, count);
	}

}
