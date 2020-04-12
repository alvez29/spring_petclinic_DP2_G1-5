package org.springframework.samples.petclinic.model;

import java.util.concurrent.TimeUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Table(name = "resulttime")
@Getter
@Setter
@Entity
public class ResultTime extends BaseEntity {
	
	@ManyToOne
	@JoinColumn(name = "pet_id")
	private Pet pet;
	
	@ManyToOne
	@JoinColumn(name = "tournament_id")
	private Tournament tournament;

	@Column(name = "time")
	private Double time;
	
	@Column(name = "lowfails")
	private int LowFails;
	
	@Column(name = "mediumfails")
	private int MediumFails;
	
	@Column(name = "bigfails")
	private int BigFails;
	
	public ResultTime() {
		
	}
	
	//   Horas | 60 MINUTOS | 60 SEGUNDOS | 1000 MILISEGUNDOS
	
//	public String getTime() {
//		return String.format("%02d:%02d:%02d.%02d", TimeUnit.MILLISECONDS.toHours(this.time),
//	            TimeUnit.MILLISECONDS.toMinutes(this.time) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(this.time)),
//	            TimeUnit.MILLISECONDS.toSeconds(this.time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(this.time)),
//	            TimeUnit.MILLISECONDS.toMillis(this.time)- TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(this.time)));
//	}
	
//	public void setTime(int hours, int min, int seconds, int millis) {
//		this.time = TimeUnit.HOURS.toMillis(hours) + TimeUnit.MINUTES.toMillis(min) + TimeUnit.SECONDS.toMillis(seconds) + TimeUnit.MILLISECONDS.toMillis(millis);
//	}
//	
//	public void setTime(Long time) {
//		this.time = time;
//	}
//	
}
