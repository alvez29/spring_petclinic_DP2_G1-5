package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

/**
 * Domain object that represents a Judge
 * 
 * @author Acunnia
 *
 */

@Entity
@Table(name = "judges")
public class Judge extends Person {
	@Column(name = "contact")
	@NotEmpty
	private String contact;
	
	@Column(name = "city")
	@NotEmpty
	private String city;

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}
