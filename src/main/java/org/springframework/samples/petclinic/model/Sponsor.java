package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.URL;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sponsor")
public class Sponsor extends NamedEntity{

	//nombre
	//dinero
	//url
	
	//@Id
//	@Column(name = "name")
//	@NotEmpty
//	private String name;
	
	@Column(name = "money")
	@NotNull
	private Double money;
	
	@Column(name = "url")
	@URL
	private String url;
	
	@ManyToOne
	@JoinColumn(name = "tournament_id")
	private Tournament tournament;


	public Tournament getTournament() {
		return tournament;
	}

	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}
	
	
	
	
}
