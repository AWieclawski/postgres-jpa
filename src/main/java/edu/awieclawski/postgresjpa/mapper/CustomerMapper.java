package edu.awieclawski.postgresjpa.mapper;

import edu.awieclawski.postgresjpa.dto.CustomerData;
import edu.awieclawski.postgresjpa.entities.Customer;

public class CustomerMapper {

	public static Customer toEntity(CustomerData customerData) {
		Customer customer = Customer.builder().id(customerData.getId()).firstName(customerData.getFirstName())
				.lastName(customerData.getLastName()).email(customerData.getEmail())
				.isDeleted(customerData.getIsDeleted()).createdBy(customerData.getCreatedBy())
				.createdDate(customerData.getCreatedDate()).build();
		return customer;
	}

	public static CustomerData toDto(Customer customer) {
		CustomerData customerData = CustomerData.builder().id(customer.getId()).firstName(customer.getFirstName())
				.lastName(customer.getLastName()).email(customer.getEmail()).isDeleted(customer.getIsDeleted())
				.createdBy(customer.getCreatedBy()).createdDate(customer.getCreatedDate()).build();
		return customerData;
	}

}
