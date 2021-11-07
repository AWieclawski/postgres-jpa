package edu.awieclawski.postgresjpa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = Customer.TABLE_NAME)
public class Customer extends Auditable<Long> {

	public static final String TABLE_NAME = "customers";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NonNull // Lombok -> RequiredArgsConstructor
	@Column(updatable = true, name = "surname", nullable = false, length = 50)
	private String firstName;

	@NonNull // Lombok -> RequiredArgsConstructor
	@Column(updatable = true, name = "name", nullable = false, length = 50)
	private String lastName;

	@NonNull // Lombok -> RequiredArgsConstructor
	@Column(updatable = true, name = "email", nullable = true, length = 50)
	private String email;

	@Builder(builderMethodName = "customerBuilder")
	public static Customer createInstance(String firstName, String lastName, String email) {
		if (email == null)
			email = "-";
		return new Customer(firstName, lastName, email);
	}

	@Builder(builderMethodName = "fullCustomerBuilder")
	public static Customer createFullInstance(Long id, String firstName, String lastName, String email) {
		return new Customer(id, firstName, lastName, email);
	}

}
