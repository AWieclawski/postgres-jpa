package edu.awieclawski.postgresjpa.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.awieclawski.postgresjpa.dto.CustomerData;
import edu.awieclawski.postgresjpa.entities.Customer;
import edu.awieclawski.postgresjpa.mapper.CustomerMapper;
import edu.awieclawski.postgresjpa.repositories.CustomerRepository;
import edu.awieclawski.postgresjpa.services.CustomerService;

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
		if (customerList != null)
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
		return populateCustomerData(customerRepository.findById(customerId).orElseThrow(
				() -> new EntityNotFoundException(String.format("Customer with id=%d not found", customerId))));
	}

	/**
	 * Get customer by ID. The service will send the customer data else will throw
	 * the exception.
	 * 
	 * @param customerId
	 * @return CustomerData
	 */
//	@Override
	public CustomerData getActiveCustomerById(Long customerId) {
		Optional<Customer> customerOpt = (isAdmin() ? customerRepository.findById(customerId)
				: customerRepository.findOneActiveCustomer(customerId));
		return populateCustomerData(customerOpt.orElseThrow(
				() -> new EntityNotFoundException(String.format("Customer with id=%d not found", customerId))));
	}

	/**
	 * Internal method to convert Customer JPA entity to the DTO object for
	 * front-end data
	 * 
	 * @param customer
	 * @return CustomerData
	 */
	private CustomerData populateCustomerData(final Customer customer) {
		return CustomerMapper.toDto(customer);
	}

	/**
	 * Method to map the front-end customer object to the JPA customer entity.
	 * 
	 * @param customerData
	 * @return Customer
	 */
	private Customer populateCustomerEntity(CustomerData customerData) {
		return CustomerMapper.toEntity(customerData);
	}

	private Boolean isAdmin() {
		return true; // TODO get authority logic 
	}

}
