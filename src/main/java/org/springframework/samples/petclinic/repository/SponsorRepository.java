package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Sponsor;

public interface SponsorRepository {

	List<Sponsor> findSponsors() throws DataAccessException;
	
	void save(Sponsor sponsor) throws DataAccessException;


}
