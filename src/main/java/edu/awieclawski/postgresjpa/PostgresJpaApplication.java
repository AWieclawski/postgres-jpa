package edu.awieclawski.postgresjpa;

import java.util.Arrays;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import edu.awieclawski.postgresjpa.config.AppRoles;
import edu.awieclawski.postgresjpa.entities.Customer;
import edu.awieclawski.postgresjpa.entities.Role;
import edu.awieclawski.postgresjpa.entities.User;
import edu.awieclawski.postgresjpa.repositories.CustomerRepository;
import edu.awieclawski.postgresjpa.repositories.RoleRepository;
import edu.awieclawski.postgresjpa.repositories.UserRepository;

/**
 * 
 * edu.awieclawski.postgresjpa.PostgresJpaApplication
 * 
 * @author AWieclawski
 *
 * 
 */
@EnableJpaAuditing
@SpringBootApplication
public class PostgresJpaApplication {

	private static final Logger log = LoggerFactory.getLogger(PostgresJpaApplication.class);
	private BCryptPasswordEncoder bCryptPasswordEncoder;

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

	/**
	 * Inserts examples if Users table is empty
	 * 
	 * @param repository
	 * @return
	 */
	@Bean
	public CommandLineRunner demoUsers(UserRepository repository, RoleRepository roleRepository) {
		return (args) -> {

			if (roleRepository.findAllRoles().size() < 1) {
				roleRepository.save(Role.builder().role(AppRoles.ADMIN.getRoleName()).build());
				roleRepository.save(Role.builder().role(AppRoles.USER.getRoleName()).build());
			}

			if (repository.findAllActiveUsers().size() < 1) {
				repository.save(User.builder().userName("OberAdmin")
						.password(bCryptPasswordEncoder.encode("12345678"))
						.email("admin@admin.op")
						.roles(new HashSet<Role>(Arrays.asList(roleRepository.findByRole(AppRoles.ADMIN.getRoleName()),
								roleRepository.findByRole(AppRoles.USER.getRoleName()))))
						.build());
				repository.save(User.builder().userName("SimpleUser")
						.password(bCryptPasswordEncoder.encode("123456"))
						.email("user@simple.com")
						.roles(new HashSet<Role>(Arrays.asList(roleRepository.findByRole(AppRoles.USER.getRoleName()))))
						.build());

				// fetch all users

				for (User user : repository.findAll()) {
					log.warn(user.toString());
				}
			}
		};
	}

}
