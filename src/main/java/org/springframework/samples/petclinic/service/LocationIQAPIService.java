package org.springframework.samples.petclinic.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.samples.petclinic.model.locationiqapi.Place;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class LocationIQAPIService {

	@LocalServerPort
	int randomPort;
	
	public static Place[] getPlace(String text) throws UnsupportedEncodingException {
		
		RestTemplate restTemplate = new RestTemplate();
    	
    	URLEncoder.encode(text, "UTF-8");
    	text.replace(" ", "%20");

    	String uri = "https://us1.locationiq.com/v1/search.php?key=c79a116f8c3259&q="+text+"&format=json";
    	
		try {
			Place[] result = restTemplate.getForObject(uri, Place[].class);

			return result;
		} catch (HttpClientErrorException ex) {
			return null;
		}

	}

}
