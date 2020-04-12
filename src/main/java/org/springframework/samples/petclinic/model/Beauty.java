package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "beauty")
@DiscriminatorValue("Beauty")
public class Beauty extends Tournament {

	public Beauty() {
	}
	
    @Column(name = "place")
    public String place;

	@OneToMany(mappedBy = "tournament")
	private Set<ResultScore> scores;

}
