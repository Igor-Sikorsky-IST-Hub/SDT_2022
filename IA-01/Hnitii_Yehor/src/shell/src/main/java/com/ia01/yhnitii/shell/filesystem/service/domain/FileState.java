package com.ia01.yhnitii.shell.filesystem.service.domain;

import lombok.extern.slf4j.Slf4j;

public interface FileState {

	void log(StatedFile statedFile);

	@Slf4j
	enum States implements FileState {
		EMPTY {
			@Override
			public void log(StatedFile statedFile) {
				statedFile.setState(EMPTY);
			}
		},
		ZIPPING_STARTED {
			@Override
			public void log(StatedFile statedFile) {
				log.info("Zipping started: {}", statedFile.getPath());
				statedFile.setState(ZIPPING_FINISHED);
			}
		},
		ZIPPING_FINISHED {
			@Override
			public void log(StatedFile statedFile) {
				log.info("Zipping finished: {}", statedFile.getPath());
				statedFile.setState(EMPTY);
			}
		}
	}

}