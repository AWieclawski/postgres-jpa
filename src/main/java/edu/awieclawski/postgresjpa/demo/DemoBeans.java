package edu.awieclawski.postgresjpa.demo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
import edu.awieclawski.postgresjpa.credentials.repositories.RoleRepository;
import edu.awieclawski.postgresjpa.credentials.services.UserService;
import edu.awieclawski.postgresjpa.dto.CustomerData;
import edu.awieclawski.postgresjpa.services.CustomerService;

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
		try {
			log.warn(" ==> starts demoCustomers");
			demoCustomers();
			log.warn(" ==> starts demoRoles");
			demoRoles();
			log.warn(" ==> starts demoUsers");
			demoUsers();
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	public void demoCustomers() throws EntityNotFoundException {
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

	public void demoRoles() throws EntityNotFoundException {
		if (roleRepository.findAll().size() < 1) {
			roleRepository.save(Role.builder().name(AppRoles.ADMIN.getRoleName()).build());
			roleRepository.saveAndFlush(Role.builder().name(AppRoles.USER.getRoleName()).build());
		}

		// fetch all roles

		for (Role role : roleRepository.findAll()) {
			log.warn(" -- role=" + role.toString());
		}
	}

	public void demoUsers() throws EntityNotFoundException {
		if (userService.findAll().size() < 1) {

			User userAdmin = User.builder().username("OberAdmin").password("123456789")
					.roles(getAdminRoles(roleRepository)).build();
			if (userAdmin.getRoles().size() > 0)
				userService.save(userAdmin);

			User user = User.builder().username("SimpleUser").password("123456789").roles(getUserRoles(roleRepository))
					.build();
			if (user.getRoles().size() > 0)
				userService.save(user);

			// fetch all users

			for (User userTmp : userService.findAll()) {
				log.warn(" -- userTmp=" + userTmp.toString());
			}
		}
	}

	private Set<Role> getAdminRoles(RoleRepository roleRepository) throws EntityNotFoundException {
		Role roleAdmin = roleRepository.findByName(AppRoles.ADMIN.getRoleName())
				.orElseThrow(() -> getRoleException(AppRoles.ADMIN.getRoleName()));
		Role roleUser = roleRepository.findByName(AppRoles.USER.getRoleName())
				.orElseThrow(() -> getRoleException(AppRoles.USER.getRoleName()));
		return new HashSet<Role>(Arrays.asList(roleAdmin, roleUser));
	}

	private Set<Role> getUserRoles(RoleRepository roleRepository) throws EntityNotFoundException {
		Role roleUser = roleRepository.findByName(AppRoles.USER.getRoleName())
				.orElseThrow(() -> getRoleException(AppRoles.USER.getRoleName()));
		return new HashSet<Role>(Arrays.asList(roleUser));
	}

	private EntityNotFoundException getRoleException(String role) {
		return new EntityNotFoundException("Role not found" + role);
	}

}
