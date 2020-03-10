package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name= "races")
@DiscriminatorValue("Race")
public class Race extends Tournament{

	@Column(name = "canodrome")
	private String canodrome;
	
	
}
