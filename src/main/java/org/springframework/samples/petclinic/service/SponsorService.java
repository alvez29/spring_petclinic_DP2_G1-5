package org.springframework.samples.petclinic.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Sponsor;
import org.springframework.samples.petclinic.repository.springdatajpa.SponsorRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedSponsorNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class SponsorService {

	@Autowired
	private SponsorRepository sponsorRepo;
	
	@Transactional
	public int sponsorCount() {
		return (int) sponsorRepo.count();
	}
	
	
	@Transactional(rollbackFor = DuplicatedSponsorNameException.class)
	public void saveSponsor(Sponsor sponsor) throws DataAccessException, DuplicatedSponsorNameException {
		Sponsor otherSponsor = sponsor.getTournament().getSponsorwithIdDifferent(sponsor.getName(), sponsor.getId());
		if (StringUtils.hasLength(sponsor.getName()) && (otherSponsor!=null && otherSponsor.getId()!=sponsor.getId())) {
			throw new DuplicatedSponsorNameException();
		}else {
			sponsorRepo.save(sponsor);
		}
	}
	
	
}
