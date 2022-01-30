package edu.awieclawski.postgresjpa.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import edu.awieclawski.postgresjpa.dto.SubCustomerData;
import edu.awieclawski.postgresjpa.entities.Customer;
import edu.awieclawski.postgresjpa.entities.SubCustomer;
import edu.awieclawski.postgresjpa.mapper.SubCustomerMapper;
import edu.awieclawski.postgresjpa.repositories.SubCustomerRepository;
import edu.awieclawski.postgresjpa.services.SubCustomerService;

/**
 * The simple service which will interact with the JPA repository to perform
 * database operations, according to:
 *
 * https://www.javadevjournal.com/spring-boot/spring-boot-with-hibernate/
 * 
 * @author AWiecawski
 *
 */
@Service("subCustomerService")
@Transactional
public class SubCustomerServiceImpl implements SubCustomerService {

	private final SubCustomerRepository subCustomerRepository;
	private final EntityManager entityManager;

	public SubCustomerServiceImpl(SubCustomerRepository subCustomerRepository, EntityManager entityManager) {
		super();
		this.subCustomerRepository = subCustomerRepository;
		this.entityManager = entityManager;
	}

	/**
	 * Create a customer based on the data sent to the service class.
	 * 
	 * @param subCustomerData
	 * @return DTO representation of the customer
	 */
	@Override
	public SubCustomerData saveSubCustomer(SubCustomerData subCustomerData, boolean shouldPersist) {
		SubCustomer subCustomerModel = populateSubCustomerEntity(subCustomerData);
		return populateCustomerData(shouldPersist ? subCustomerRepository.saveAndFlush(subCustomerModel)
				: subCustomerRepository.save(subCustomerModel));
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
	public boolean deleteSubCustomer(Long customerId) {
		subCustomerRepository.deleteById(customerId);
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
	public boolean updateSubCustomerIsDeleted(Long customerId) {
		return subCustomerRepository.updateCustomerIsDeleted(customerId) > 0;
	}

	/**
	 * Method to return list of all the available customers in the system.
	 * 
	 * @return list of customer
	 */
	@Override
	public List<SubCustomerData> getAllSubCustomers() {
		List<SubCustomerData> subCustomers = new ArrayList<>();
		List<SubCustomer> subCustomersEntityList = subCustomerRepository.findAll();
		if (subCustomersEntityList != null)
			subCustomersEntityList.forEach(subCustomerEntity -> {
				subCustomers.add(populateCustomerData(subCustomerEntity));
			});
		return subCustomers;
	}

	/**
	 * Get customer by ID. The service will send the customer data else will throw
	 * the exception.
	 * 
	 * @param customerId
	 * @return CustomerData
	 */
	@Override
	public SubCustomerData getSubCustomerById(Long customerId) {
		return populateCustomerData(subCustomerRepository.findById(customerId).orElseThrow(
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
	public SubCustomerData getActiveSubCustomerById(Long customerId) {
		Optional<SubCustomer> customerOpt = (isAdmin() ? subCustomerRepository.findById(customerId)
				: subCustomerRepository.findOneActiveCustomer(customerId));
		return populateCustomerData(customerOpt.orElseThrow(
				() -> new EntityNotFoundException(String.format("Customer with id=%d not found", customerId))));
	}

	/**
	 * Internal method to convert Customer JPA entity to the DTO object for
	 * front-end data
	 * 
	 * @param subCustomer
	 * @return CustomerData
	 */
	private SubCustomerData populateCustomerData(final SubCustomer subCustomer) {
		return SubCustomerMapper.toDto(assignCustomer(subCustomer));
	}

	/**
	 * Method to map the front-end customer object to the JPA customer entity.
	 * 
	 * @param subCustomerData
	 * @return Customer
	 */
	private SubCustomer populateSubCustomerEntity(SubCustomerData subCustomerData) {
		return assignCustomer(SubCustomerMapper.toEntity(subCustomerData));
	}

	private SubCustomer assignCustomer(SubCustomer subCustomer) {
		Customer customerEntity = subCustomer.getCustomer();
		if (customerEntity != null && customerEntity.getId() > 0) {
			subCustomer.setCustomer(entityManager.getReference(Customer.class, customerEntity.getId()));
//			customerEntity.addSubCustomer(subCustomer);
		}
		return subCustomer;
	}

	private Boolean isAdmin() {
		return true; // TODO get authority logic
	}

}
