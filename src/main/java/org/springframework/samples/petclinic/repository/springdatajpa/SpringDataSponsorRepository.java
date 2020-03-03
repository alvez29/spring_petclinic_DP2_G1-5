package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Sponsor;
import org.springframework.samples.petclinic.repository.SponsorRepository;

public interface SpringDataSponsorRepository extends SponsorRepository, Repository<Sponsor, Integer> {

	@Override
	@Query("SELECT sponsor FROM Sponsor sponsor ORDER BY sponsor.name")
	List<Sponsor> findSponsors() throws DataAccessException;
	
	
}
