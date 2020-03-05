package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.URL;

import com.sun.istack.NotNull;

@Entity
@Table(name = "sponsor")
public class Sponsor extends NamedEntity{

	//nombre
	//dinero
	//url
	
	//@Id
	@Column(name = "name")
	@NotEmpty
	private String name;
	
	@Column(name = "money")
	@NotNull
	private Double money;
	
	@Column(name = "url")
	@URL
	private String url;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
}
