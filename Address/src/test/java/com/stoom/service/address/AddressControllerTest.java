package com.stoom.service.address;

import static com.stoom.common.Json.parse;
import static com.stoom.common.Json.stringify;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.SpringApplication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.stoom.common.HttpClientAdapter;


/**
 * Test class for Rest services implemented in {@link AddressController}.
 * 
 * @author Rodrigo
 *
 */
public class AddressControllerTest {

	private static String BASE_URL = "http://localhost:8080";
	
	@BeforeClass
	public static void startUp() {
		SpringApplication.run(AddressServiceLoader.class, new String[]{});

		HttpClientAdapter client = new HttpClientAdapter(BASE_URL + "/address/ping");
		do {
			client.get();
		} while (client.getHttpStatus() != HttpStatus.SC_OK);
	}


	@Test
	public void testListAll() {
		HttpClientAdapter client = new HttpClientAdapter(BASE_URL + "/address/all");
		String response = client.get();
		
		assertEquals(HttpStatus.SC_OK, client.getHttpStatus());
		assertTrue(isNotBlank(response));
		
		try {
			Collection<Address> addressCollection = parse(response, new TypeReference<Collection<Address>>() {});
			assertNotNull(addressCollection);
			assertFalse(addressCollection.isEmpty());
			assertTrue(addressCollection.iterator().next() instanceof Address);

		} catch (Exception e) {
			fail("Payload does not conform to a collection of Addresses.");
		}
	}


	@Test
	public void testGetAddress() {

		HttpClientAdapter client = new HttpClientAdapter(BASE_URL + "/address/all");
		String response = client.get();
		
		assertEquals(HttpStatus.SC_OK, client.getHttpStatus());
		assertTrue(isNotBlank(response));

		Collection<Address> addressCollection = Collections.emptyList();
		try {
			addressCollection = parse(response, new TypeReference<Collection<Address>>() {});

		} catch (IOException e) {
			fail("Payload does not conform to a collection of Addresses.");
		}
		
		Address toGet = addressCollection.iterator().next();
		
		client = new HttpClientAdapter(BASE_URL + "/address/" + String.valueOf(toGet.getId()));
		response = client.get();
		
		assertEquals(HttpStatus.SC_OK, client.getHttpStatus());
		assertTrue(isNotBlank(response));
		
		Address address;
		try {
			address = parse(response, Address.class);
			assertNotNull(address.getId());
			assertNotNull(address.getStreetName());
			assertEquals(toGet.getId(), address.getId());

		} catch (Exception e) {
			fail("Payload does not conform to object of type Address.");
		}
	}


	@Test
	public void testGetInvalid() {

		HttpClientAdapter client = new HttpClientAdapter(BASE_URL + "/address/1b");
		String response = client.get();
		
		assertEquals(HttpStatus.SC_BAD_REQUEST, client.getHttpStatus());
		assertTrue(isBlank(response));

	}

	
	@Test
	public void testGetNotFound() {

		HttpClientAdapter client = new HttpClientAdapter(BASE_URL + "/address/1213141");
		String response = client.get();
		
		assertEquals(HttpStatus.SC_NOT_FOUND, client.getHttpStatus());
		assertTrue(isBlank(response));

	}
	
	@Test
	public void testDeleteInvalid() {

		HttpClientAdapter client = new HttpClientAdapter(BASE_URL + "/address/1b");
		String response = client.delete();
		
		assertEquals(HttpStatus.SC_BAD_REQUEST, client.getHttpStatus());
		assertTrue(isBlank(response));

	}

	
	@Test
	public void testDelete() {
		HttpClientAdapter client = new HttpClientAdapter(BASE_URL + "/address/all");
		String response = client.get();
		
		assertEquals(HttpStatus.SC_OK, client.getHttpStatus());
		assertTrue(isNotBlank(response));

		Collection<Address> addressCollection = Collections.emptyList();
		try {
			addressCollection = parse(response, new TypeReference<Collection<Address>>() {});

		} catch (IOException e) {
			fail("Payload does not conform to a collection of Addresses.");
		}
		
		Address toDelete = addressCollection.iterator().next();
		
		client = new HttpClientAdapter(BASE_URL + "/address/" + String.valueOf(toDelete.getId()));
		client.delete();
		assertEquals(HttpStatus.SC_OK, client.getHttpStatus());

		response = client.get();
		assertEquals(HttpStatus.SC_NOT_FOUND, client.getHttpStatus());
		assertTrue(isBlank(response));
	}


	@Test
	public void testSaveInsertFail1() throws JsonProcessingException {
		HttpClientAdapter client = new HttpClientAdapter(BASE_URL + "/address/");
		String httpResponse = client.put("", "aplication/json");

		assertEquals(HttpStatus.SC_BAD_REQUEST, client.getHttpStatus());
		assertFalse(isBlank(httpResponse));
		assertEquals("null payload", httpResponse);
	}

	@Test
	public void testSaveInsertFail2() throws JsonProcessingException {
		HttpClientAdapter client = new HttpClientAdapter(BASE_URL + "/address/");
		String httpResponse = client.put("garbage", "aplication/json");

		assertEquals(HttpStatus.SC_BAD_REQUEST, client.getHttpStatus());
		assertFalse(isBlank(httpResponse));
		assertEquals("Invalid format", httpResponse);
	}

	
	@Test
	public void testSaveInsertValidation() throws JsonProcessingException {

		Address newAddress = new Address("Rua de baixo", "101", null, "Limoeiro", "Campinas", null, "Brasil").withZipCode("13-000");
		String payload = stringify(newAddress);

		HttpClientAdapter client = new HttpClientAdapter(BASE_URL + "/address/");
		String httpResponse = client.put(payload, "aplication/json");
		
		assertEquals(HttpStatus.SC_BAD_REQUEST, client.getHttpStatus());
		assertFalse(isBlank(httpResponse));
		assertEquals("Field 'state' is mandatory", httpResponse);

	}

	
	@Test
	public void testSaveInsert() throws JsonProcessingException {

		Address newAddress = new Address("Rua de baixo", "101", null, "Limoeiro", "Campinas", "SP", "Brasil").withZipCode("13-000");
		String payload = stringify(newAddress);

		HttpClientAdapter client = new HttpClientAdapter(BASE_URL + "/address/");
		String httpResponse = client.put(payload, "aplication/json");
		
		assertEquals(HttpStatus.SC_OK, client.getHttpStatus());
		assertFalse(isBlank(httpResponse));

	}

	
	@Test
	public void testSaveUpdate() throws JsonProcessingException {

// 		find all addresses

		HttpClientAdapter client = new HttpClientAdapter(BASE_URL + "/address/all");
		String response = client.get();
		
		assertEquals(HttpStatus.SC_OK, client.getHttpStatus());
		assertTrue(isNotBlank(response));

		Collection<Address> addressCollectionBefore = Collections.emptyList();
		try {
			addressCollectionBefore = parse(response, new TypeReference<Collection<Address>>() {});

		} catch (IOException e) {
			fail("Payload does not conform to a collection of Addresses.");
		}
	
		int updated = 0;

//		update all states to "Minas Gerais" where state == "MG"

		for (Address each : addressCollectionBefore) {
			if ("MG".equals(each.getState())) {

				each.setState("Minas Gerais");
				String payload = stringify(each);

				HttpClientAdapter updateclient = new HttpClientAdapter(BASE_URL + "/address/");
				String httpResponse = updateclient.put(payload, "aplication/json");
				updated++;
				
				assertEquals(HttpStatus.SC_OK, updateclient.getHttpStatus());
				assertFalse(isBlank(httpResponse));
			}			
		}
		
		assertTrue("At least 1 should be updated, or the test is flawed.", updated > 0);

//		find all addresses again, and validate that all were updated, none was inserted

		response = client.get();
		
		assertEquals(HttpStatus.SC_OK, client.getHttpStatus());
		assertTrue(isNotBlank(response));

		Collection<Address> addressCollectionAfter = Collections.emptyList();
		try {
			addressCollectionAfter = parse(response, new TypeReference<Collection<Address>>() {});

		} catch (IOException e) {
			fail("Payload does not conform to a collection of Addresses.");
		}

		assertEquals("Number of addresses should not change", addressCollectionBefore.size(), addressCollectionAfter.size());

		for (Address each : addressCollectionBefore) {
			assertNotEquals("State should have been updated", "MG", each.getState());
		}

	}
}
