package com.ia01.yhnitii.shell.history.repository.entity.enumeration;

public enum Action {

	COPY(true),
	TRANSFER(true),
	DELETE(false),
	NEW_FILE(false),
	NEW_FOLDER(false);

	private final boolean requireTwoPaths;

	Action(boolean requireTwoPaths) {
		this.requireTwoPaths = requireTwoPaths;
	}

	public boolean requireTwoPaths() {
		return this.requireTwoPaths;
	}

}
