package com.checkinone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.checkinone.thymeleaf.AppDialect;

@SpringBootApplication
public class CheckinoneApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheckinoneApplication.class, args);
	}

	@Bean
	AppDialect appDialect() {
		return new AppDialect();
	}
}
