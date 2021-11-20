package edu.awieclawski.postgresjpa.services.impl;

import edu.awieclawski.postgresjpa.entities.Customer;
import edu.awieclawski.postgresjpa.repositories.CustomerRepository;
import edu.awieclawski.postgresjpa.dto.CustomerData;
import edu.awieclawski.postgresjpa.services.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * The simple service which will interact with the JPA repository to perform
 * database operations, according to:
 *
 * https://www.javadevjournal.com/spring-boot/spring-boot-with-hibernate/
 * 
 * @author AWiecawski
 *
 */
@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	/**
	 * Create a customer based on the data sent to the service class.
	 * 
	 * @param customer
	 * @return DTO representation of the customer
	 */
	@Override
	public CustomerData saveCustomer(CustomerData customer) {
		Customer customerModel = populateCustomerEntity(customer);
		return populateCustomerData(customerRepository.save(customerModel));
	}

	/**
	 * Delete customer based on the customer ID.We can also use other option to
	 * delete customer based on the entity (passing JPA entity class as method
	 * parameter)
	 * 
	 * @param customerId
	 * @return boolean flag indicating the request status
	 */
	@Override
	public boolean deleteCustomer(Long customerId) {
		customerRepository.deleteById(customerId);
		return true;
	}

	/**
	 * Delete customer based on the customer ID.We can also use other option to
	 * delete customer based on the entity (passing JPA entity class as method
	 * parameter)
	 * 
	 * @param customerId
	 * @return boolean flag indicating the request status
	 */
	@Override
	public boolean updateCustomerIsDeleted(Long customerId) {
		return customerRepository.updateCustomerIsDeleted(customerId) > 0;
	}

	/**
	 * Method to return list of all the available customers in the system.
	 * 
	 * @return list of customer
	 */
	@Override
	public List<CustomerData> getAllCustomers() {
		List<CustomerData> customers = new ArrayList<>();
		List<Customer> customerList = customerRepository.findAll();
		customerList.forEach(customer -> {
			customers.add(populateCustomerData(customer));
		});
		return customers;
	}

	/**
	 * Get customer by ID. The service will send the customer data else will throw
	 * the exception.
	 * 
	 * @param customerId
	 * @return CustomerData
	 */
	@Override
	public CustomerData getCustomerById(Long customerId) {
		return populateCustomerData(customerRepository.findById(customerId)
				.orElseThrow(() -> new EntityNotFoundException("Customer not found")));
	}

	/**
	 * Internal method to convert Customer JPA entity to the DTO object for
	 * front-end data
	 * 
	 * @param customer
	 * @return CustomerData
	 */
	private CustomerData populateCustomerData(final Customer customer) {
		CustomerData customerData = CustomerData.builder().id(customer.getId()).firstName(customer.getFirstName())
				.lastName(customer.getLastName()).email(customer.getEmail()).isDeleted(customer.getIsDeleted())
				.createdBy(customer.getCreatedBy()).createdDate(customer.getCreatedDate()).build();
		return customerData;
	}

	/**
	 * Method to map the front-end customer object to the JPA customer entity.
	 * 
	 * @param customerData
	 * @return Customer
	 */
	private Customer populateCustomerEntity(CustomerData customerData) {
		Customer customer = Customer.builder().id(customerData.getId()).firstName(customerData.getFirstName())
				.lastName(customerData.getLastName()).email(customerData.getEmail())
				.isDeleted(customerData.getIsDeleted()).createdBy(customerData.getCreatedBy())
				.createdDate(customerData.getCreatedDate()).build();
		return customer;
	}

}
