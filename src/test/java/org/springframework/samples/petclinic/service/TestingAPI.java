package org.springframework.samples.petclinic.service;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.samples.petclinic.model.locationiqapi.Place;
import org.springframework.test.context.junit4.SpringRunner;

	@RunWith(SpringRunner.class)
	@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
	public class TestingAPI {
	 
	    @LocalServerPort
	    int randomServerPort;
	 
	    @Test
	    @Disabled
	    public void getPlace() throws URISyntaxException, UnsupportedEncodingException {
	       
	    	String text = "La Giralda";
	    	
	    	Place[] places = LocationIQAPIService.getPlace(text);
	    	
	    	System.out.println(places[0].getLat());
	    	
	    	Assertions.assertThat(places[0].getLat()).isEqualTo("37.386207");
	    	Assertions.assertThat(places[0].getLon()).isEqualTo("-5.99255572619863");
	    }
	}
	

