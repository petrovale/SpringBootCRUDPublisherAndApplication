package com.isakov.springboot;

import com.isakov.springboot.configuration.JpaConfiguration;
import com.isakov.springboot.model.App;
import com.isakov.springboot.model.Genre;
import com.isakov.springboot.model.Publisher;
import com.isakov.springboot.model.Version;
import com.isakov.springboot.repository.AppRepository;
import com.isakov.springboot.repository.GenreRepository;
import com.isakov.springboot.repository.PublisherRepository;
import com.isakov.springboot.repository.VersionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import(JpaConfiguration.class)
@SpringBootApplication(scanBasePackages={"com.isakov.springboot"})// same as @Configuration @EnableAutoConfiguration @ComponentScan
public class SpringBootCRUDApp {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCRUDApp.class, args);
	}

	/**
	 * Save students and courses to H2 DB for testing
	 * @param publisherRepository
	 * @param appRepository
	 * @return
	 */
	@Bean
	public CommandLineRunner demo(PublisherRepository publisherRepository, AppRepository appRepository, GenreRepository genreRepository, VersionRepository versionRepository) {
		return (args) -> {
			// Create users with BCrypt encoded password (user/user, admin/admin)
			Publisher publisher1 = new Publisher("user", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER");
			Publisher publisher2 = new Publisher("admin", "$2a$08$bCCcGjB03eulCWt3CY0AZew2rVzXFyouUolL5dkL/pBgFkUH9O4J2", "ROLE_ADMIN");
			publisher1 = publisherRepository.save(publisher1);
			publisherRepository.save(publisher2);

			// save Henres
			Genre genre1 = genreRepository.save(new Genre("Programming Java"));
			Genre genre2 = genreRepository.save(new Genre("Spring Boot basics"));

			// save apps
			App app1 = appRepository.save(new App("Destroyers publisher1", publisher1, genre2));
			appRepository.save(new App("Screwdriver publisher1", publisher1, genre1));
			appRepository.save(new App("Collector publisher1", publisher1));
			appRepository.save(new App("Developer publisher1", publisher1));

			appRepository.save(new App("Destroyers publisher2", publisher2));
			appRepository.save(new App("Screwdriver publisher2", publisher2));

			versionRepository.save(new Version("Version 1", false, app1, publisher1));
			versionRepository.save(new Version("Version 2", false, app1, publisher1));
			versionRepository.save(new Version("Version 3", false, app1, publisher1));

		};
	}
}
