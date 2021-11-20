package edu.awieclawski.postgresjpa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import edu.awieclawski.postgresjpa.audits.Auditable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@SuperBuilder

/**
 * 
 * 
 * @author AWieclawski
 *
 */
@Entity
@Table(name = Email.TABLE_NAME)
public class Email extends Auditable<String> {
// Auditable {

	public static final String TABLE_NAME = "emails";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@EqualsAndHashCode.Include
	@Column(updatable = true, name = "name", nullable = false, length = 50)
	private String name;

	@EqualsAndHashCode.Include
	@Column(updatable = true, name = "domain", nullable = false, length = 50)
	private String domain;

	@Override
	public String toString() {
		return String.format("%s@%s", name, domain);
	}

}
