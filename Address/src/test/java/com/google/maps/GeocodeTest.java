package com.google.maps;

import org.junit.Test;

import com.stoom.common.HttpClientAdapter;

import static com.stoom.common.Json.*;
import static org.junit.Assert.*;
import static org.apache.commons.lang3.StringUtils.*;

public class GeocodeTest {

	private final String BASE_URL = "https://maps.googleapis.com/maps/api/geocode/json";

	@Test
	public void test1() {
		String url = BASE_URL + "?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key=AIzaSyDTK0igIQTCi5EYKL9tzOIJ9N6FUASGZos";

		HttpClientAdapter client = new HttpClientAdapter(url);
		String response = client.get();

		assertNotNull(response);
	}
}
