package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Table(name = "resultscore")
@Setter
@Getter
@Entity
public class ResultScore extends BaseEntity {
	
	@ManyToOne
	@JoinColumn(name = "pet_id")
	private Pet pet;
	
	@ManyToOne
	@JoinColumn(name = "tournament_id")
	private Tournament tournament;
	
	@Column(name = "haircut")
	private int haircut;
	
	@Column(name = "haircutdif")
	private int haircutdif;
	
	@Column(name = "technique")
	private int technique;
	
	@Column(name = "posture")
	private int posture;
	
	@Transient
	public Integer getTotalPoints(){
		Integer res = this.haircut +
				this.haircutdif +
				this.technique +
				this.posture;
		
		return res;
	}
}
