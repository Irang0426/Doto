package com.doto.doto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DotoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DotoApplication.class, args);
	}
}
