package edu.awieclawski.postgresjpa.dto;

import edu.awieclawski.postgresjpa.audits.Auditable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

/**
 * An object that carries Customer data between processes.
 * 
 * Extends the same abstract class as Customer does
 * 
 * @author AWieclawski
 *
 */
public class CustomerData extends Auditable<String> {

	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private Boolean isDeleted;

}
