package edu.awieclawski.postgresjpa.mapper;

import edu.awieclawski.postgresjpa.dto.SubCustomerData;
import edu.awieclawski.postgresjpa.entities.SubCustomer;

public class SubCustomerMapper {

	public static SubCustomer toEntity(SubCustomerData customerData) {
		SubCustomer customer = SubCustomer.builder().id(customerData.getId()).customer(
				customerData.getCustomerData() != null ? CustomerMapper.toEntity(customerData.getCustomerData()) : null)
				.orderId(customerData.getOrderId()).firstName(customerData.getFirstName())
				.lastName(customerData.getLastName()).email(customerData.getEmail())
				.isDeleted(customerData.getIsDeleted()).createdBy(customerData.getCreatedBy())
				.createdDate(customerData.getCreatedDate()).build();
		return customer;
	}

	public static SubCustomerData toDto(SubCustomer customer) {
		SubCustomerData customerData = SubCustomerData.builder().id(customer.getId())
				.customerData(customer.getCustomer() != null ? CustomerMapper.toDto(customer.getCustomer()) : null)
				.firstName(customer.getFirstName()).lastName(customer.getLastName()).email(customer.getEmail())
				.isDeleted(customer.getIsDeleted()).createdBy(customer.getCreatedBy())
				.createdDate(customer.getCreatedDate()).build();
		return customerData;
	}

}
