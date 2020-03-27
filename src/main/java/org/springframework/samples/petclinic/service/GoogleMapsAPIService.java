package org.springframework.samples.petclinic.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.samples.petclinic.model.googlemapsapi.Place;
import org.springframework.web.client.RestTemplate;

public class GoogleMapsAPIService {

	@LocalServerPort
	int randomPort;
	
	public static Place getPlace(String text) throws UnsupportedEncodingException {
		
    	RestTemplate restTemplate = new RestTemplate();
    	
    	URLEncoder.encode(text, "UTF-8");
    	text.replace(" ", "+");

    	String uri = "https://maps.googleapis.com/maps/api/place/textsearch/json?query="+text+"&key=AIzaSyDgX8xoAMBWfHWQJGIJ1Njq1PHz5jBg1x8";
    	
    	Place result = restTemplate.getForObject(uri, Place.class);

		return result;
	}
	
}
