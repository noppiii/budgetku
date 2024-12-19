package com.budgetku.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BudgetkuBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BudgetkuBackendApplication.class, args);
	}

}
