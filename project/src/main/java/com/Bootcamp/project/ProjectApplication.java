package com.Bootcamp.project;

import com.Bootcamp.project.Auditing.SpringAuditorAware;
import com.Bootcamp.project.controller.AdminController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class ProjectApplication {

	@Bean
	public AuditorAware<String> auditorAware(){
		return new SpringAuditorAware();

	}

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

}
