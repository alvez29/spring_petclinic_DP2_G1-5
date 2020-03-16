
package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Table(name = "hability")
@Getter
@Setter
@Entity
@DiscriminatorValue("Hability")
public class Hability extends Tournament {

	public Hability() {

	}


	@Column(name = "circuit")
	public String circuit;

}
