package com.ia01.yhnitii.shell;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ShellApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShellApplication.class, args);
	}

}
