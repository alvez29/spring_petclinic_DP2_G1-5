package org.springframework.samples.petclinic.projections;

import java.time.LocalDate;

public interface ListTournament {
	
	Integer getId();
	String getName();
	LocalDate getDate();
	Double getRewardMoney();
	String getStatus();

}
