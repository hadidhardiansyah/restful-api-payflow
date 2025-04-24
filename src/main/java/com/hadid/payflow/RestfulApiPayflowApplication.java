package com.hadid.payflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RestfulApiPayflowApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestfulApiPayflowApplication.class, args);
	}

}
