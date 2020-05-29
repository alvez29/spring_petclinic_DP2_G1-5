/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.omg.PortableInterceptor.ORBInitInfoPackage.DuplicateName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.ResultTime;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.exceptions.ClinicNotAuthorisedException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration test of the Service and the Repository layer.
 * <p>
 * ClinicServiceSpringDataJpaTests subclasses benefit from the following services provided
 * by the Spring TestContext Framework:
 * </p>
 * <ul>
 * <li><strong>Spring IoC container caching</strong> which spares us unnecessary set up
 * time between test execution.</li>
 * <li><strong>Dependency Injection</strong> of test fixture instances, meaning that we
 * don't need to perform application context lookups. See the use of
 * {@link Autowired @Autowired} on the <code>{@link
 * ClinicServiceTests#clinicService clinicService}</code> instance variable, which uses
 * autowiring <em>by type</em>.
 * <li><strong>Transaction management</strong>, meaning each test method is executed in
 * its own transaction, which is automatically rolled back by default. Thus, even if tests
 * insert or otherwise change database state, there is no need for a teardown or cleanup
 * script.
 * <li>An {@link org.springframework.context.ApplicationContext ApplicationContext} is
 * also inherited and can be used for explicit bean lookup if necessary.</li>
 * </ul>
 *
 * @author Ken Krebs
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @author Dave Syer
 */

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class PetServiceTests {        
        @Autowired
	protected PetService petService;
        
        @Autowired
	protected OwnerService ownerService;	

        @Autowired
    protected TournamentService tournamentService;
        
        @Autowired
    protected RaceResultService raceResultService;
        
	@Test
	void shouldFindPetWithCorrectId() {
		Pet pet7 = this.petService.findPetById(7);
		assertThat(pet7.getName()).startsWith("Samantha");
		assertThat(pet7.getOwner().getFirstName()).isEqualTo("Jean");
	}

	@Test
	void shouldFindAllPetTypes() {
		Collection<PetType> petTypes = this.petService.findPetTypes();

		PetType petType1 = EntityUtils.getById(petTypes, PetType.class, 1);
		assertThat(petType1.getName()).isEqualTo("Beagle");
		PetType petType4 = EntityUtils.getById(petTypes, PetType.class, 4);
		assertThat(petType4.getName()).isEqualTo("German shepherd");
	}
	
	@Test
	void shouldNotFindThatPetType() {
		Collection<PetType> petTypes = this.petService.findPetTypes();
		
		String notRegisteredType = "Mutt";
		assertThat(petTypes.stream().map(x->x.getName()).collect(Collectors.toList())).doesNotContain(notRegisteredType);
		
	}
	
	

	@Test
	@Transactional
	@Disabled
	public void shouldInsertPetIntoDatabaseAndGenerateId() {
		Owner owner6 = this.ownerService.findOwnerById(6);
		int found = owner6.getPets().size();

		Pet pet = new Pet();
		pet.setName("bowser");
		Collection<PetType> types = this.petService.findPetTypes();
		pet.setType(EntityUtils.getById(types, PetType.class, 2));
		pet.setBirthDate(LocalDate.now());
		owner6.addPet(pet);
		assertThat(owner6.getPets().size()).isEqualTo(found + 1);

            try {
                this.petService.savePet(pet);
            } catch (DuplicatedPetNameException ex) {
                Logger.getLogger(PetServiceTests.class.getName()).log(Level.SEVERE, null, ex);
            }
		this.ownerService.saveOwner(owner6);

		owner6 = this.ownerService.findOwnerById(6);
		assertThat(owner6.getPets().size()).isEqualTo(found + 1);
		// checks that id has been generated
		assertThat(pet.getId()).isNotNull();
	}
	
	@Test
	@Transactional
	public void shouldThrowExceptionInsertingPetsWithTheSameName() {
		Owner owner6 = this.ownerService.findOwnerById(6);
		Pet pet = new Pet();
		pet.setName("wario");
		Collection<PetType> types = this.petService.findPetTypes();
		pet.setType(EntityUtils.getById(types, PetType.class, 2));
		pet.setBirthDate(LocalDate.now());
		owner6.addPet(pet);
		try {
			petService.savePet(pet);		
		} catch (DuplicatedPetNameException e) {
			// The pet already exists!
			e.printStackTrace();
		}
		
		Pet anotherPetWithTheSameName = new Pet();		
		anotherPetWithTheSameName.setName("wario");
		anotherPetWithTheSameName.setType(EntityUtils.getById(types, PetType.class, 1));
		anotherPetWithTheSameName.setBirthDate(LocalDate.now().minusWeeks(2));
		Assertions.assertThrows(DuplicatedPetNameException.class, () ->{
			owner6.addPet(anotherPetWithTheSameName);
			petService.savePet(anotherPetWithTheSameName);
		});		
	}

	@Test
	@Transactional
	public void shouldUpdatePetName() throws Exception {
		Pet pet7 = this.petService.findPetById(7);
		String oldName = pet7.getName();

		String newName = oldName + "X";
		pet7.setName(newName);
		this.petService.savePet(pet7);

		pet7 = this.petService.findPetById(7);
		assertThat(pet7.getName()).isEqualTo(newName);
	}
	
	@Test
	@Transactional
	public void shouldThrowExceptionUpdatingPetsWithTheSameName() {
		Owner owner6 = this.ownerService.findOwnerById(6);
		Pet pet = new Pet();
		pet.setName("wario");
		Collection<PetType> types = this.petService.findPetTypes();
		pet.setType(EntityUtils.getById(types, PetType.class, 2));
		pet.setBirthDate(LocalDate.now());
		owner6.addPet(pet);
		
		Pet anotherPet = new Pet();		
		anotherPet.setName("waluigi");
		anotherPet.setType(EntityUtils.getById(types, PetType.class, 1));
		anotherPet.setBirthDate(LocalDate.now().minusWeeks(2));
		owner6.addPet(anotherPet);
		
		try {
			petService.savePet(pet);
			petService.savePet(anotherPet);
		} catch (DuplicatedPetNameException e) {
			// The pets already exists!
			e.printStackTrace();
		}				
			
		Assertions.assertThrows(DuplicatedPetNameException.class, () ->{
			anotherPet.setName("wario");
			petService.savePet(anotherPet);
		});		
	}

	
	
	//----------------------Test for User Story #11----------------------------------- 
	
	@Test
	@Transactional
	@Disabled
	public void shouldAddNewVisitForPet() throws DataAccessException, ClinicNotAuthorisedException {
		Pet pet7 = this.petService.findPetById(7);
		int found = pet7.getVisits().size();
		Visit visit = new Visit();
		pet7.addVisit(visit);
		visit.setDescription("test");
		visit.setCompetitionCheck("PASSED");
		visit.setClinic("Dog And Cat Hospital");
		this.petService.saveVisit(visit);
            try {
                this.petService.savePet(pet7);
            } catch (DuplicatedPetNameException ex) {
                Logger.getLogger(PetServiceTests.class.getName()).log(Level.SEVERE, null, ex);
            }

		pet7 = this.petService.findPetById(7);
		assertThat(pet7.getVisits().size()).isEqualTo(found + 1);
		assertThat(visit.getId()).isNotNull();
	}
	
//	
//	@Test
//	@Transactional
//	public void shouldAddNewVisitWithoutCompetitionCheckAndInventedClinic() throws DataAccessException, ClinicNotAuthorisedException, DuplicatedPetNameException{
//		Visit visit = new Visit();
//		visit.setDescription("This a test 1");
//		visit.setCompetitionCheck("-");
//		visit.setClinic("Does not exist");
//		
//		this.petService.saveVisit(visit);
//		assertThat(visit.getId()).isNotNull();
//		
//	}
//	
//	
//	//Test del escenario positivo
//	@Test
//	@Transactional
//	public void shouldAddNewVisitWithCompetitionCheckAndCorrectClinic() throws DataAccessException, ClinicNotAuthorisedException, DuplicatedPetNameException{
//		Visit visit = new Visit();
//		visit.setDescription("This a test 2");
//		visit.setCompetitionCheck("PASSED");
//		visit.setClinic("Canin Vet");
//		
//		this.petService.saveVisit(visit);
//		assertThat(visit.getId()).isNotNull();
//	}
//	
//	
//	//Test del escenario negativo
//	@Test
//	@Transactional
//	public void shouldNotAddNewVisitWithCompetitionCheckAndInventedClinic() throws DataAccessException, ClinicNotAuthorisedException, DuplicatedPetNameException{
//		Visit visit = new Visit();
//		visit.setDescription("This a test 3");
//		visit.setCompetitionCheck("NOT PASSED");
//		visit.setClinic("Invent Vet");
//		
//		try {
//			this.petService.saveVisit(visit);
//		}catch(ClinicNotAuthorisedException ex){
//            Logger.getLogger(PetServiceTests.class.getName()).log(Level.SEVERE, null, ex);
//		}
//		assertThat(visit.getId()).isNull();
//	}
//	
//	
	
	
	@ParameterizedTest
	@CsvSource({"This is a test 1, -, Does not Exist","This is is a test 2, PASSED, Canin Vet","This is is a test 3, NOT PASSED, Invent Vet"})
	public void showAddNewVisit(String description, String competitionCheck,
			String clinic) {
		Visit visit = new Visit();
		visit.setDescription(description);
		visit.setCompetitionCheck(competitionCheck);
		visit.setClinic(clinic);
		
		try {
			this.petService.saveVisit(visit);
            assertThat(visit.getId()).isNotNull();
		}catch(ClinicNotAuthorisedException ex){
            Logger.getLogger(PetServiceTests.class.getName()).log(Level.SEVERE, null, ex);
            assertThat(visit.getId()).isNull();
		}
		
	}
	
	
	@Test
	@Disabled
	void shouldFindVisitsByPetId() throws Exception {
		Collection<Visit> visits = this.petService.findVisitsByPetId(7);
		assertThat(visits.size()).isEqualTo(2);
		Visit[] visitArr = visits.toArray(new Visit[visits.size()]);
		assertThat(visitArr[0].getPet()).isNotNull();
		assertThat(visitArr[0].getDate()).isNotNull();
		assertThat(visitArr[0].getPet().getId()).isEqualTo(7);
	}
	
	@Test
	void shouldSelectActualWinner() throws Exception {
		Tournament tournament = tournamentService.findTournamentById(1);
		assertThat(tournament.getDate().isBefore(LocalDate.now().plusYears(1)));
		List<ResultTime> ls = raceResultService.findByTournamnetId(1);
		Collections.sort(ls, (x, y) -> x.getTime().compareTo(y.getTime()));
		Pet pet = ls.get(0).getPet();
		boolean res = petService.isActualWinner(pet.getId());
		assertThat(res);
	}
	
	

}
