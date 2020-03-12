package org.springframework.samples.petclinic.repository.springdatajpa;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Race;
import org.springframework.stereotype.Repository;

@Repository
public interface RaceRepository extends CrudRepository<Race, Integer>{
	
	@Query(value = "SELECT date FROM Tournaments where date >= CURRENT_DATE", nativeQuery = true)
	public List<String> getFutureDates();

}