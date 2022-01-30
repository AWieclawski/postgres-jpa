package edu.awieclawski.postgresjpa.demo;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import edu.awieclawski.postgresjpa.config.AppRoles;
import edu.awieclawski.postgresjpa.credentials.entities.Role;
import edu.awieclawski.postgresjpa.credentials.entities.User;
import edu.awieclawski.postgresjpa.credentials.entities.UserRegistration;
import edu.awieclawski.postgresjpa.credentials.repositories.RoleRepository;
import edu.awieclawski.postgresjpa.credentials.services.UserService;
import edu.awieclawski.postgresjpa.dto.CustomerData;
import edu.awieclawski.postgresjpa.dto.SubCustomerData;
import edu.awieclawski.postgresjpa.entities.Customer;
import edu.awieclawski.postgresjpa.repositories.CustomerRepository;
import edu.awieclawski.postgresjpa.repositories.SubCustomerRepository;
import edu.awieclawski.postgresjpa.services.CustomerService;
import edu.awieclawski.postgresjpa.services.SubCustomerService;

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

	private final CustomerService customerService;
	private final CustomerRepository customerRepository;
	private final SubCustomerService subCustomerService;
	private final SubCustomerRepository subCustomerRepository;
	private final RoleRepository roleRepository;
	private final UserService userService;

	public DemoBeans(CustomerService customerService, SubCustomerService subCustomerService,
			RoleRepository roleRepository, UserService userService, CustomerRepository customerRepository,
			SubCustomerRepository subCustomerRepository) {

		super();
		this.customerService = customerService;
		this.customerRepository = customerRepository;
		this.subCustomerService = subCustomerService;
		this.roleRepository = roleRepository;
		this.userService = userService;
		this.subCustomerRepository = subCustomerRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		demoCustomers();
		demoSubCustomers();
		demoSubCustomersBis();
		demoRoles();
		demoUsers();
	}

	public void demoCustomers() {
		if (!customerService.getAllCustomers().iterator().hasNext()) {
			customerService.saveCustomer(
					CustomerData.builder().firstName("John").lastName("Doeski").email("john@test.com").build());
			customerService.saveCustomer(
					CustomerData.builder().firstName("David").lastName("Dobrik").email("david@test.com").build());
			customerService.saveCustomer(
					CustomerData.builder().firstName("Robert").lastName("Hickle").email("robert@email.com").build());
			customerService.saveCustomer(CustomerData.builder().firstName("Edgar").lastName("Smith").build());

			// fetch all customers

			for (CustomerData customer : customerService.getAllCustomers()) {
				log.warn(" -- customer=" + customer.toString());
			}
		}
	}

	public void demoSubCustomers() {
		if (!subCustomerService.getAllSubCustomers().iterator().hasNext()) {
			CustomerData customerOne = customerService.getCustomerById(1L);
			subCustomerService.saveSubCustomer(SubCustomerData.builder().customerData(customerOne).firstName("Ola")
					.lastName("Hull").email("ola@test.com").build(), true);
//			customerService.saveCustomer(customerOne);
			CustomerData customerTwo = customerService.getCustomerById(2L);
			subCustomerService.saveSubCustomer(SubCustomerData.builder().customerData(customerTwo).firstName("Ana")
					.lastName("Bull").email("ana@test.com").build(), true);
			CustomerData customerThree = customerService.getCustomerById(3L);
			subCustomerService.saveSubCustomer(SubCustomerData.builder().customerData(customerThree).firstName("Dora")
					.lastName("Pitson").email("dora@email.com").build(), true);
//			customerService.saveCustomer(customerThree);
			CustomerData customerFour = customerService.getCustomerById(4L);
			subCustomerService.saveSubCustomer(
					SubCustomerData.builder().customerData(customerFour).firstName("Molly").lastName("Blondy").build(),
					true);

		}

		// fetch all subCustomers

		for (SubCustomerData subCustomer : subCustomerService.getAllSubCustomers()) {
			log.warn(" -- subCustomer=" + subCustomer.toString());
		}
	}

	public void demoSubCustomersBis() {
		CustomerData customerOne = customerService.getCustomerById(1L);
		Long id = customerOne.getId();
		log.warn(" -- CustomerId=" + id);
		log.warn(" -- findSubCustomersByCustomerId=" + subCustomerRepository.findSubCustomersByCustomerId(id));

		CustomerData customerThree = customerService.getCustomerById(3L);
		id = customerThree.getId();
		log.warn(" -- CustomerId=" + id);
		log.warn(" -- findSubCustomersByCustomerId=" + subCustomerRepository.findSubCustomersByCustomerId(id));

		subCustomerService.saveSubCustomer(SubCustomerData.builder().customerData(customerOne).firstName("Ala")
				.lastName("Dicker").email("ala@test.com").build(), false);
		subCustomerService.saveSubCustomer(SubCustomerData.builder().customerData(customerThree).firstName("Lara")
				.lastName("Lettermann").email("lara@email.com").build(), false);

//		customerService.saveCustomer(customerOne);
//		customerService.saveCustomer(customerThree);

		id = customerOne.getId();
		log.warn(" -- CustomerId=" + id);
		log.warn(" -- findSubCustomersByCustomerId=" + subCustomerRepository.findSubCustomersByCustomerId(id));

		id = customerThree.getId();
		log.warn(" -- CustomerId=" + id);
		log.warn(" -- findSubCustomersByCustomerId=" + subCustomerRepository.findSubCustomersByCustomerId(id));

		Customer customerOneEntity = customerRepository.findById(customerOne.getId()).orElse(null);
		log.warn(" -- customerOneEntity=" + customerOneEntity.getId() + ", " + customerOneEntity.getSubCustomersSet());

		Customer customerTwoEntity = customerRepository.findById(customerThree.getId()).orElse(null);
		log.warn(" -- customerTwoEntity=" + customerTwoEntity.getId() + ", " + customerTwoEntity.getSubCustomersSet());

//		customerService.saveCustomer(customerOne);
//		customerService.saveCustomer(customerThree);

		// fetch all subCustomers

		for (SubCustomerData subCustomer : subCustomerService.getAllSubCustomers()) {
			log.warn(" -- -- subCustomer=" + subCustomer.toString());
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
