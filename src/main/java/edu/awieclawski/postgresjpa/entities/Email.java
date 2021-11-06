package edu.awieclawski.postgresjpa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)

/**
 * 
 * 
 * @author AWieclawski
 *
 */
@Entity
@Table(name = Email.TABLE_NAME)
public class Email extends Auditable<String> {

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

	public String toString() {
		return name + "@" + domain;
	}

}
