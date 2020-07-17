package com.stoom.service.address.geocode;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;

import com.stoom.common.HttpClientAdapter;
import com.stoom.common.Json;

public class GeocodeTest {

	private final String BASE_URL = "https://maps.googleapis.com/maps/api/geocode/json";

	@Test
	public void test1() {
		String url = BASE_URL + "?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key=AIzaSyDTK0igIQTCi5EYKL9tzOIJ9N6FUASGZos";

		HttpClientAdapter client = new HttpClientAdapter(url);
		String payload = client.get();

		assertNotNull(payload);
		
		GeocodeApiResponse response;
		try {
			response = Json.parse(payload, GeocodeApiResponse.class);
			assertTrue("Google returned not OK response", response.isOk());
			assertEquals(2, response.getResults().size());
			GeocodeResult result = response.getFirstResult();
			assertNotNull(result);
			assertNotNull(result.getLatitude());
			assertNotNull(result.getLongitude());
			
		} catch (IOException e) {
			fail("Error parsing payload");
		}	
	}
	
	@Test
	public void test2() {
		String url = BASE_URL + "?address=0+Rua+dos+Bobos,+Belo+Horizonte,+CA&key=AIzaSyDTK0igIQTCi5EYKL9tzOIJ9N6FUASGZos";

		HttpClientAdapter client = new HttpClientAdapter(url);
		String payload = client.get();

		assertNotNull(payload);
		
		GeocodeApiResponse response;
		try {
			response = Json.parse(payload, GeocodeApiResponse.class);
			assertTrue(response.isOk());
			assertEquals(1, response.getResults().size());
			GeocodeResult result = response.getFirstResult();
			assertNotNull(result);
			assertNotNull(result.getLatitude());
			assertNotNull(result.getLongitude());
			
		} catch (IOException e) {
			fail("Error parsing payload");
		}	
	}
}
