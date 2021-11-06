package edu.awieclawski.postgresjpa.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(onlyExplicitlyIncluded = true)

/**
 * 
 * @author AWieclawski
 *
 */
@Entity
@Table(name = Customer.TABLE_NAME)
public class Customer extends Auditable<String> {
	public static final String TABLE_NAME = "customers";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ToString.Include
	@EqualsAndHashCode.Include
	@Column(updatable = true, name = "surname", nullable = false, length = 50)
	private String firstName;

	@ToString.Include
	@EqualsAndHashCode.Include
	@Column(updatable = true, name = "name", nullable = false, length = 50)
	private String lastName;

	@ToString.Include
	@EqualsAndHashCode.Include
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "email_id", nullable = false)
	private Email email;

}
