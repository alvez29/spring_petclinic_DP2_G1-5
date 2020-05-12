package org.springframework.samples.petclinic.repository.springdatajpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentRepository extends CrudRepository<Tournament, Integer>{

	@Query(value="select dtype from tournaments where id = ?1 ",nativeQuery=true)
	String findTournamentType(int tournamentId);

	@Query(value="select canodrome from tournaments where id = ?1 ",nativeQuery=true)
	String getCanodrome(int tournamentId);
	
	@Query(value="select circuit from tournaments where id = ?1 ",nativeQuery=true)
	String getCircuit(int tournamentId);
	
	@Query(value="select place from tournaments where id = ?1 ",nativeQuery=true)
	String getPlace(int tournamentId);


}
