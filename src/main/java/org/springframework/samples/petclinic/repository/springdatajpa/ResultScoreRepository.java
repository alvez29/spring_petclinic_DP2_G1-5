package org.springframework.samples.petclinic.repository.springdatajpa;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.ResultScore;

public interface ResultScoreRepository extends CrudRepository<ResultScore, Integer> {

	@Query("select r from ResultScore r where r.tournament.id = ?1")
	List<ResultScore> findByTournamentId(int id);


}
