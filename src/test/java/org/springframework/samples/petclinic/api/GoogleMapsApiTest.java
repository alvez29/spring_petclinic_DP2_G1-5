package org.springframework.samples.petclinic.api;

import static io.restassured.RestAssured.when;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import lombok.extern.java.Log;

@Log
public class GoogleMapsApiTest {
	
	@Test
	public void findLaGiralda() {
		when()
			.get("https://us1.locationiq.com/v1/search.php?key=c79a116f8c3259&q=Giralda&format=json")
		.then()
			.statusCode(200)
		.and()
			.assertThat()
				.body("lat", equalTo("37.386207"))
				.body("lot", equalTo("-5.99255572619863"))
			.and()
				.time(lessThan(25L), TimeUnit.SECONDS);
			
	}
	
}
