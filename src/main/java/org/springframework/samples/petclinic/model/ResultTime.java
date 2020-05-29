
package org.springframework.samples.petclinic.model;

import java.beans.Transient;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.Getter;
import lombok.Setter;

@Table(name = "resulttime")
@Getter
@Setter
@Entity
public class ResultTime extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "pet_id")
	private Pet			pet;

	@ManyToOne
	@JoinColumn(name = "tournament_id")
	private Tournament	tournament;

	@Column(name = "time")
	@Range(min = 0, message = "It must be a positive number")
	@NotNull
	private Double		time;

	@Range(min = 0, message = "It must be a positive number")
	@Column(name = "lowfails")
	private int			LowFails;

	@Range(min = 0, message = "It must be a positive number")
	@Column(name = "mediumfails")
	private int			MediumFails;

	@Range(min = 0, message = "It must be a positive number")
	@Column(name = "bigfails")
	private int			BigFails;


	@Transient
	public Double getTotalResult() {
		int lowFails = this.LowFails * 1;
		int mediumFails = this.MediumFails * 3;
		int bigFails = this.BigFails * 5;
		Double totalResult = this.time + (lowFails + mediumFails + bigFails);
		return totalResult;
	}


}
