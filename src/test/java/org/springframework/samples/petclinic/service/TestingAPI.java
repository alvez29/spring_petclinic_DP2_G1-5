package org.springframework.samples.petclinic.service;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.googlemapsapi.Place;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

	@RunWith(SpringRunner.class)
	@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
	public class TestingAPI {
	 
	    @LocalServerPort
	    int randomServerPort;
	 
	    @Test
	    public void getPlace() throws URISyntaxException, UnsupportedEncodingException {
	       
	    	String text = "Hotel Mairena";
	    	
	    	Place place = GoogleMapsAPIService.getPlace(text);
	    	
	    	place.getResults();
	    }
	}
	

