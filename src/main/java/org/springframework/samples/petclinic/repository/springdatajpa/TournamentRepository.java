package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.projections.ListTournament;
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

	@Query(value="select dtype,id,name,capacity,date,reward_money,status,place,circuit,canodrome,type_id from tournament_pets join tournaments where pet_id = ?1 and tournament_id = id" ,nativeQuery=true)
	List<Tournament> findTounamentsByPetId(Integer petId);

	@Query("select t.name as name, t.date as date, t.id as id, t.rewardMoney as rewardMoney, t.status as status from Tournament t")
	List<ListTournament> findAllTournamentsP() throws DataAccessException;
}
