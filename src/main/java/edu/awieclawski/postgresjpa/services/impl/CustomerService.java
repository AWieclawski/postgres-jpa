package edu.awieclawski.postgresjpa.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;

//import java.util.Optional;

import org.springframework.stereotype.Service;

import edu.awieclawski.postgresjpa.dto.CustomerData;
import edu.awieclawski.postgresjpa.repository.CustomerRepository;
import edu.awieclawski.postgresjpa.entities.Customer;
import edu.awieclawski.postgresjpa.services.I_CustomerService;

/**
 * acc. to:
 * https://www.javadevjournal.com/spring-boot/spring-boot-with-hibernate/
 *
 */
@Service
public class CustomerService implements I_CustomerService {

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
	 * Method to return list of all the available customers in the system.This is a
	 * simple implementation but you might want to use pagination in the real world
	 * example.
	 * 
	 * @return list of customer
	 */
	@Override
	public Iterable<CustomerData> getAllCustomers() {
		List<CustomerData> customers = new ArrayList<>();
		List<Customer> customerList = customerRepository.findAll();
		customerList.forEach(customer -> {
			customers.add(populateCustomerData(customer));
		});
		return customers;
	}

	/**
	 * Get customer by ID.The service will send the customer data else will throw
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
	 * Internal method to convert Customer JPA entity to the DTO object for frontend
	 * data
	 * 
	 * @param customer
	 * @return CustomerData
	 */
	private CustomerData populateCustomerData(final Customer customer) {
		CustomerData customerData = new CustomerData();
		customerData.setId(customer.getId());
		customerData.setFirstName(customer.getFirstName());
		customerData.setLastName(customer.getLastName());
		customerData.setEmail(customer.getEmail());
		return customerData;
	}

	/**
	 * Method to map the frontend customer object to the JPA customer entity.
	 * 
	 * @param customerData
	 * @return Customer
	 */
	private Customer populateCustomerEntity(CustomerData customerData) {
		Customer customer = new Customer();
		customer.setFirstName(customerData.getFirstName());
		customer.setLastName(customerData.getLastName());
		customer.setEmail(customerData.getEmail());
		return customer;
	}

}
