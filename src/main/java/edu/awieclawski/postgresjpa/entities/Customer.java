package edu.awieclawski.postgresjpa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import edu.awieclawski.postgresjpa.audits.Auditable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder

/**
 * 
 * @author AWieclawski
 *
 */
@Entity
@Table(name = Customer.TABLE_NAME)
public class Customer extends Auditable<Long> {

	public static final String TABLE_NAME = "customers";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(updatable = true, name = "surname", nullable = false, length = 50)
	private String firstName;

	@Column(updatable = true, name = "name", nullable = false, length = 50)
	private String lastName;

	@Column(updatable = true, name = "email", nullable = true, length = 50)
	private String email;

}
