package com.dron.jsontool.config.utils;

public interface Constants {

	interface User {
		String WRONG_PASSWORD = "Password is wrong!!!";
		String USER_ALREADY_EXISTS = "User with this email already exists!";
	}

	interface JsonSchema {
		String JSON_SCHEMA_NOT_FOUND = "Json schema not found";
		String PREV_JSON_SCHEMA_NOT_FOUND = "Previous json schema not found";
		String NEXT_JSON_SCHEMA_NOT_FOUND = "Next json schema not found";
	}

}
