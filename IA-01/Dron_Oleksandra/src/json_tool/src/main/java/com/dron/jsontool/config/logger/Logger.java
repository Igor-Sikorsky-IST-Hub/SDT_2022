package com.dron.jsontool.config.logger;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Logger {

	private static final Logger INSTANCE = new Logger();

	private Logger() {
	}

	public static Logger getInstance() {
		return INSTANCE;
	}

	public void info(String message) {
		log.info(message);
	}

	public void info(String var1, Object... var2) {
		log.info(var1, var2);
	}

	public void error(String error, Throwable throwable) {
		log.error(error);
	}

	public void error(String error) {
		log.error(error);
	}

}
