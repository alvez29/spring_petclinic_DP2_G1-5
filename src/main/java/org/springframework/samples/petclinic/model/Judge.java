package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

/**
 * Domain object that represents a Judge
 * 
 * @author Acunnia
 *
 */

@Entity
@Getter
@Setter
@Table(name = "judges")
public class Judge extends Person {
	
	@Column(name = "first_name")
	@NotEmpty
	private String firstName;

	@Column(name = "last_name")
	@NotEmpty
	private String lastName;
	
	@Column(name = "contact")
	@NotEmpty
	private String contact;
	
	@Column(name = "city")
	@NotEmpty
	private String city;
}
