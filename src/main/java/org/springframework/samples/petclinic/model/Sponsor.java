package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.URL;

import com.sun.istack.NotNull;

public class Sponsor {

	//nombre
	//dinero
	//url
	
	@Column(name = "sponsor_name")
	@NotEmpty
	private String name;
	
	@NotNull
	private Double money;
	
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
