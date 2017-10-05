package com.isakov.springboot;

import com.isakov.springboot.configuration.JpaConfiguration;
import com.isakov.springboot.model.Publisher;
import com.isakov.springboot.repositories.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.HashSet;
import java.util.Set;


@Import(JpaConfiguration.class)
@SpringBootApplication(scanBasePackages={"com.isakov.springboot"})// same as @Configuration @EnableAutoConfiguration @ComponentScan
public class SpringBootCRUDApp {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCRUDApp.class, args);
	}

	/**
	 * Save students and courses to H2 DB for testing
	 * @param repository
	 * @return
	 */
	@Bean
	public CommandLineRunner demo(PublisherRepository repository) {
		return (args) -> {
			/*
			// save students
			Student student1 = new Student("John", "Johnson", "IT", "john@john.com");
			repository.save(new Student("Steve", "Stevens", "IT", "steve.stevent@st.com"));
			repository.save(new Student("Mary", "Robinson", "IT", "mary@robinson.com"));
			repository.save(new Student("Kate", "Keystone", "Nursery","kate@kate.com"));
			repository.save(new Student("Diana", "Doll", "Business","diana@doll.com"));

			Course course1 = new Course("Programming Java");
			Course course2 = new Course("Spring Boot basics");
			crepository.save(new Course("Marketing 1"));
			crepository.save(new Course("Marketing 2"));

			crepository.save(course1);
			crepository.save(course2);

			Set<Course> courses = new HashSet<Course>();
			courses.add(course1);
			courses.add(course2);

			student1.setCourses(courses);
			repository.save(student1);
*/
			// Create users with BCrypt encoded password (user/user, admin/admin)
			Publisher publisher1 = new Publisher("user", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER");
			Publisher publisher2 = new Publisher("admin", "$2a$08$bCCcGjB03eulCWt3CY0AZew2rVzXFyouUolL5dkL/pBgFkUH9O4J2", "ADMIN");
			repository.save(publisher1);
			repository.save(publisher2);
		};
	}
}
