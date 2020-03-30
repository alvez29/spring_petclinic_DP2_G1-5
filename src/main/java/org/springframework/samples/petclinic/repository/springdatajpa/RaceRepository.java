package org.springframework.samples.petclinic.repository.springdatajpa;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Judge;
import org.springframework.samples.petclinic.model.Race;
import org.springframework.samples.petclinic.model.Sponsor;
import org.springframework.stereotype.Repository;

@Repository
public interface RaceRepository extends CrudRepository<Race, Integer>{
	
	@Query(value = "SELECT date FROM Tournaments where date >= CURRENT_DATE", nativeQuery = true)
	public List<String> getFutureDates();

	@Query(value = "SELECT date FROM Tournaments where id = ?1", nativeQuery = true)
	public LocalDate getDateByRaceId(int raceId);

	@Query("SELECT s from Sponsor s where tournament.id = ?1")
	public Collection<Sponsor> getSponsors(int raceId);

	
	@Query("SELECT t.judges from Tournament t where t.id = ?1 ")
	public Collection<Judge> getJudges(Integer id);

}