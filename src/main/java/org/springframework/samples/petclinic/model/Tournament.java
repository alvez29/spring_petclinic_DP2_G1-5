package org.springframework.samples.petclinic.model;

import java.beans.Transient;
import java.time.LocalDate;
import java.util.List;

import javax.money.format.MonetaryParseException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.javamoney.moneta.Money;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.number.money.MonetaryAmountFormatter;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tournaments")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
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
	
	@OneToMany(mappedBy = "tournament")
	private List<Sponsor> sponsors;
	
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
