package com.dron.jsontool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
public class JsonToolApplication {

	public static void main(String[] args) {
		SpringApplication.run(JsonToolApplication.class, args);
	}

}
