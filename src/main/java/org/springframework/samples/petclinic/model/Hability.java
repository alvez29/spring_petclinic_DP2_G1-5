
package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
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
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "tournament")
	private Set<ResultTime> scores;


	@Column(name = "circuit")
	public String circuit;

}
