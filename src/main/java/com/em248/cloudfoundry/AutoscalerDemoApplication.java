package com.em248.cloudfoundry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class AutoscalerDemoApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(AutoscalerDemoApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AutoscalerDemoApplication.class);
	}
}
