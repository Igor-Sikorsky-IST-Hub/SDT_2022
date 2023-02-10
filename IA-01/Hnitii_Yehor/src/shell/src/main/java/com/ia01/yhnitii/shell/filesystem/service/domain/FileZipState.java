package com.ia01.yhnitii.shell.filesystem.service.domain;

import lombok.extern.slf4j.Slf4j;

public interface FileZipState {

	void log(StatedFileZip statedFile);

	@Slf4j
	enum States implements FileZipState {
		EMPTY {
			@Override
			public void log(StatedFileZip statedFile) {
				statedFile.setState(EMPTY);
			}
		},
		ZIPPING_STARTED {
			@Override
			public void log(StatedFileZip statedFile) {
				log.info("Zipping started: {}", statedFile.getPath());
				statedFile.setState(ZIPPING_FINISHED);
			}
		},
		ZIPPING_FINISHED {
			@Override
			public void log(StatedFileZip statedFile) {
				log.info("Zipping finished: {}", statedFile.getPath());
				statedFile.setState(EMPTY);
			}
		}
	}

}