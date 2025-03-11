package org.seats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SeatsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeatsApplication.class, args);
	}

}
