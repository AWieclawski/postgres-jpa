package edu.awieclawski.postgresjpa.dto;

/**
 * 
 * @author AWieclawski
 *
 */
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class CustomerData {

	private Long id;
	private String firstName;
	private String lastName;
	private String email;

}
