package edu.awieclawski.postgresjpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import edu.awieclawski.postgresjpa.entities.Customer;
import edu.awieclawski.postgresjpa.repositories.CustomerRepository;

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

	private static final Logger log = LoggerFactory.getLogger(PostgresJpaApplication.class);
//	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(PostgresJpaApplication.class, args);
	}

	/**
	 * Inserts examples if Customers table is empty
	 * 
	 * @param repository
	 * @return
	 */
	@Bean
	public CommandLineRunner demo(CustomerRepository repository) {
		return (args) -> {

			if (repository.findAllActiveCustomers().size() < 1) {
				repository.save(Customer.builder().id(null).firstName("John").lastName("Doeski").email("john@test.com")
						.build());
				repository.save(Customer.builder().id(null).firstName("David").lastName("Dobrik")
						.email("david@test.com").build());
				repository.save(Customer.builder().id(null).firstName("Robert").lastName("Hickle")
						.email("robert@email.com").build());
				repository.save(Customer.builder().id(null).firstName("Edgar").lastName("Smith").build());

				// fetch all customers

				for (Customer customer : repository.findAll()) {
					log.warn(customer.toString());
				}
			}
		};
	}

}
