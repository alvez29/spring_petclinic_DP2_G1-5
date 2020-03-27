package org.springframework.samples.petclinic.repository.springdatajpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentRepository extends CrudRepository<Tournament, Integer>{

	@Query(value="SELECT DTYPE FROM TOURNAMENTS WHERE ID = ?1 ",nativeQuery=true)
	String findTournamentType(int tournamentId);

	@Query(value="SELECT CANODROME FROM TOURNAMENTS WHERE ID = ?1 ",nativeQuery=true)
	String getCanodrome(int tournamentId);
	
	@Query(value="SELECT CIRCUIT FROM TOURNAMENTS WHERE ID = ?1 ",nativeQuery=true)
	String getCircuit(int tournamentId);
	
	@Query(value="SELECT PLACE FROM TOURNAMENTS WHERE ID = ?1 ",nativeQuery=true)
	String getPlace(int tournamentId);


}
