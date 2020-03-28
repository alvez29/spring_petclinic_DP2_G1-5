package org.springframework.samples.petclinic.model;

import java.beans.Transient;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tournaments")
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Tournament extends NamedEntity{

	@Column(name = "name")
	@NotEmpty
	private String name;
	
	@Column(name = "status")
	@NotNull
	private String status;
	
	@Column(name = "date")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate date;
	
	@Min(value = 1)
	@Column(name = "reward_money")
	private Double rewardMoney;
	
	@Column(name = "capacity")
	@Min(value = 0)
	@NotNull
	private Integer capacity;
	
	@ManyToOne
	@JoinColumn(name = "type_id")
	private PetType breedRestriction;
	
	@ManyToMany
	@JoinTable(
			  name = "tournament_pets", 
			  joinColumns = @JoinColumn(name = "tournament_id"), 
			  inverseJoinColumns = @JoinColumn(name = "pet_id"))
	private List<Pet> pets;
	
	@ManyToMany
	@JoinTable(
			  name = "tournament_judges", 
			  joinColumns = @JoinColumn(name = "tournament_id"), 
			  inverseJoinColumns = @JoinColumn(name = "judge_id"))
	private List<Judge> judges;
	
	@OneToMany(mappedBy = "tournament")
	private List<Sponsor> sponsors;
	
	public void addJudge(Judge judge) {
		getJudges().add(judge);
	}
	
	public void addPet(Pet pet) {
		getPets().add(pet);
	}
	
	public void addSponsor(Sponsor sponsor) {
		getSponsors().add(sponsor);
		sponsor.setTournament(this);
	}
	
	public Sponsor getSponsorwithIdDifferent(String name, Integer id) {
		name = name.toLowerCase();
		for(Sponsor sponsor : getSponsors()) {
			String compName = sponsor.getName();
			compName = compName.toLowerCase();
			if (compName.contentEquals(name) && sponsor.getId() != id) {
				return sponsor;
			}
		}
		return null;
	}

	@Transient
	public Double getFirstClassified() {
		//Money money = Money.of(this.rewardMoney*0.5, "EUR");
		Double money = this.rewardMoney*0.5;
		return money;
	}
	
	@Transient
	public Double getSecondClassified() {
		//Money money = Money.of(this.rewardMoney*0.35, "EUR");
		Double money = this.rewardMoney*0.35;
		return money;
	}
	
	@Transient
	public Double getThirdClassified() {
		//Money money = Money.of(this.rewardMoney*0.15, "EUR");
		Double money = this.rewardMoney*0.15;
		return money;
	}
	

}
