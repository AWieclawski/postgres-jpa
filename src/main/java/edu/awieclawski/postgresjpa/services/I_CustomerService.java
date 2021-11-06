package edu.awieclawski.postgresjpa.services;

//import java.util.Optional;

import edu.awieclawski.postgresjpa.dto.CustomerData;
//import edu.awieclawski.postgresjpa.entities.Customer;

public interface I_CustomerService {

//	Customer save(Customer entity);
//
//	Optional<Customer> findById(Long id);
//
//	Customer findOne();
//
//	Iterable<Customer> findAll();

	CustomerData saveCustomer(CustomerData customer);

	boolean deleteCustomer(final Long customerId);

	Iterable<CustomerData> getAllCustomers();

	CustomerData getCustomerById(final Long customerId);

}
