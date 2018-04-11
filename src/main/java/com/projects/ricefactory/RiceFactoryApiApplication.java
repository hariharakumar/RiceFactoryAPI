package com.projects.ricefactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class RiceFactoryApiApplication extends SpringBootServletInitializer {
	// Extends initializer makes this class deployable to outside containers

	public static void main(String[] args) {

        SpringApplication.run(RiceFactoryApiApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(RiceFactoryApiApplication.class);
	}
}
