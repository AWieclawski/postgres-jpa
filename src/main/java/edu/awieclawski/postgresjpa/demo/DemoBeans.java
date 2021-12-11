package edu.awieclawski.postgresjpa.demo;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import edu.awieclawski.postgresjpa.config.AppRoles;
import edu.awieclawski.postgresjpa.credentials.entities.Role;
import edu.awieclawski.postgresjpa.credentials.entities.User;
import edu.awieclawski.postgresjpa.credentials.entities.UserRegistration;
import edu.awieclawski.postgresjpa.credentials.repositories.RoleRepository;
import edu.awieclawski.postgresjpa.credentials.services.UserService;
import edu.awieclawski.postgresjpa.dto.CustomerData;
import edu.awieclawski.postgresjpa.services.CustomerService;

/**
 * https://sztukakodu.pl/jak-zmapowac-relacje-many-to-many-i-nie-zwariowac/
 * 
 * @author awieclawski
 *
 */
@Component
@Transactional
public class DemoBeans implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(DemoBeans.class);

	@Autowired
	private CustomerService customerService;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserService userService;

	@Override
	public void run(String... args) throws Exception {
		demoCustomers();
		demoRoles();
		demoUsers();
	}

	public void demoCustomers() {
		if (customerService.getAllCustomers().iterator().hasNext()) {
			customerService.saveCustomer(CustomerData.builder().id(null).firstName("John").lastName("Doeski")
					.email("john@test.com").build());
			customerService.saveCustomer(CustomerData.builder().id(null).firstName("David").lastName("Dobrik")
					.email("david@test.com").build());
			customerService.saveCustomer(CustomerData.builder().id(null).firstName("Robert").lastName("Hickle")
					.email("robert@email.com").build());
			customerService.saveCustomer(CustomerData.builder().id(null).firstName("Edgar").lastName("Smith").build());

			// fetch all customers

			for (CustomerData customer : customerService.getAllCustomers()) {
				log.warn(" -- customer=" + customer.toString());
			}
		}
	}

	public void demoRoles() {
		if (roleRepository.findAll().size() < 1) {
			roleRepository.save(Role.builder().name(AppRoles.ADMIN.getRoleName()).build());
			roleRepository.saveAndFlush(Role.builder().name(AppRoles.USER.getRoleName()).build());
		}

		// fetch all roles

		for (Role role : roleRepository.findAll()) {
			log.warn(" -- role=" + role.toString());
		}
	}

	public void demoUsers() {
		if (userService.findAll().size() < 1) {
			User userAdmin = User.builder().username("OberAdmin").password("123456789").build();
			userAdmin.addUserRegistration(UserRegistration.builder().role(getAdminRole()).build());
			userAdmin.addUserRegistration(UserRegistration.builder().role(getUserRole()).build());
			userService.save(userAdmin);

			User userSimple = User.builder().username("SimpleUser").password("123456789").build();
			userSimple.addUserRegistration(UserRegistration.builder().role(getUserRole()).build());
			userService.save(userSimple);

			// fetch all users

			for (User userTmp : userService.findAll()) {
				log.warn(" -- userTmp=" + userTmp.toString());
			}
		}
	}

	private Role getAdminRole() {
		return roleRepository.findByName(AppRoles.ADMIN.getRoleName())
				.orElseThrow(() -> getRoleException(AppRoles.ADMIN.getRoleName()));
	}

	private Role getUserRole() {
		return roleRepository.findByName(AppRoles.USER.getRoleName())
				.orElseThrow(() -> getRoleException(AppRoles.USER.getRoleName()));
	}

	private EntityNotFoundException getRoleException(String role) {
		return new EntityNotFoundException("Role not found" + role);
	}

}
