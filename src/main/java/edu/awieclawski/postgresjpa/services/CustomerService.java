package edu.awieclawski.postgresjpa.services;

import edu.awieclawski.postgresjpa.dto.CustomerData;

public interface CustomerService {

	CustomerData saveCustomer(CustomerData customer);

	boolean deleteCustomer(final Long customerId);

	Iterable<CustomerData> getAllCustomers();

	CustomerData getCustomerById(final Long customerId);

}
