package edu.awieclawski.postgresjpa.services;

import edu.awieclawski.postgresjpa.dto.SubCustomerData;

public interface SubCustomerService {

	SubCustomerData saveSubCustomer(SubCustomerData customer, boolean shouldPersist);

	boolean deleteSubCustomer(final Long customerId);

	boolean updateSubCustomerIsDeleted(Long customerId);

	Iterable<SubCustomerData> getAllSubCustomers();

	SubCustomerData getSubCustomerById(final Long customerId);

	SubCustomerData getActiveSubCustomerById(Long customerId);

}
