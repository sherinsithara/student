package org.mentorbridge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StudentApllication {
	@RequestMapping("/")
	public String home() {
		return "Dockerized Spring Boot Application";
	}

	public static void main(String[] args) {

		SpringApplication.run(StudentApllication.class, args);
	}

}
