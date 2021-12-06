package edu.awieclawski.postgresjpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * 
 * edu.awieclawski.postgresjpa.PostgresJpaApplication
 * 
 * @author AWieclawski
 *
 *         https://www.javaguides.net/2019/09/user-account-registration-and-login.html
 */
@EnableJpaAuditing
@SpringBootApplication
public class PostgresJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostgresJpaApplication.class, args);
	}

}
