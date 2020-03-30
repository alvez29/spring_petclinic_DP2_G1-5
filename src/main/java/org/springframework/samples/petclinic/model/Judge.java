package org.springframework.samples.petclinic.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	
	@ManyToMany
	@JoinTable(
			  name = "tournament_judges", 
			  joinColumns = @JoinColumn(name = "judge_id"), 
			  inverseJoinColumns = @JoinColumn(name = "tournament_id"))
	private List<Tournament> tournaments;
	
	@Column(name = "contact")
	@NotEmpty
	private String contact;
	
	@Column(name = "city")
	@NotEmpty
	private String city;
	
	public void addTournament(Tournament tourn) {
		getTournaments().add(tourn);
	}
}
