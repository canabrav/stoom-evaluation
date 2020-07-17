package com.stoom.service.address;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.stoom.service.address.exception.ExcessiveLoadDataAccessException;
import com.stoom.service.address.exception.ValidationException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AddressServiceLoader.class)
public class AddressServiceTest {

	@Autowired
	AddressService service;

	@Test
	public void testSaveFindRemove() throws ValidationException {
		Address address = service.save(new Address("Rua dos Bobos", "1", null, "Limoeiro", "Sabará", "MG", "Brasil").withZipCode("30000-000"));
		assertNotNull(address.getId());
		
		Address same = service.findById(address.getId());
		assertNotNull(same);

		service.remove(address.getId());
		
		Address notExists = service.findById(address.getId());
		assertNull("Was previously deleted, should not have been found.", notExists);
	}


	@Test
	public void testSaveCalculateLatLong() throws ValidationException {
		Address beforeSave = new Address("R. Dr. Pereira Lima", "85", null, "Vila Industrial", "Campinas", "SP", "Brasil").withZipCode("13035-505");

		assertTrue(beforeSave.isMissingLatLong());
		assertNull(beforeSave.getLatitude());
		assertNull(beforeSave.getLongitude());

		Address address = service.save(beforeSave);
		assertNotNull(address.getId());
		
		assertFalse(address.isMissingLatLong());
		assertNotNull(address.getLatitude());
		assertNotNull(address.getLongitude());
	}

	
	@Test
	public void testSaveWithLatLong() throws ValidationException {
		Address beforeSave = new Address("Rua dos Bobos", "1", null, "Limoeiro", "Sabará", "MG", "Brasil").withZipCode("30000-000").withLatLong(-23d, -42d);

		assertFalse(beforeSave.isMissingLatLong());
		assertNotNull(beforeSave.getLatitude());
		assertNotNull(beforeSave.getLongitude());

		Address address = service.save(beforeSave);
		assertNotNull(address.getId());
		
		assertFalse(address.isMissingLatLong());
		assertEquals(Double.valueOf(-23d), address.getLatitude());
		assertEquals(Double.valueOf(-42d), address.getLongitude());
	}


	@Test
	public void testSetId() throws ValidationException {
		
		Long invalidId = 1001L;

		Address notExists = service.findById(invalidId);
		assertNull(notExists);
		

		Address address = service.save(new Address("Rua dos Bobos", "1", null, "Limoeiro", "Sabará", "MG", "Brasil").withZipCode("30000-000").withId(invalidId));
		assertNotNull(address);
		assertNotNull(address.getId());
		assertNotEquals("Persistence should not keep the id sent by the user", invalidId, address.getId());
	}

	
	@Test
	public void testUpdate() throws ValidationException {
		
		Address address = service.save(new Address("Rua dos Bobos", "1", null, "Limoeiro", "Sabará", "MG", "Brasil").withZipCode("30-000"));
		assertNotNull(address.getId());
		
		Address copy = new Address("Rua dos Bobos", "1", null, "Limoeiro", "Sabará", "MG", "Brasil").withZipCode("30000-000").withId(address.getId());
		Address merged = service.save(copy);
		
		assertNotSame(address, merged);
		assertEquals(address.getId(), merged.getId());
		
		Address updated = service.findById(address.getId());
		assertEquals("30000-000", updated.getZipCode());
	}

	
	@Test
	public void testValidation() {
		try {
			service.save(new Address("Rua dos Bobos", "", /* invalid field value */ 
					null, "Limoeiro", "Sabará", "MG", "Brasil").withZipCode("30000-000"));
			fail("Should not allow to insert, since streetName is null");

		} catch (Exception e) {
			assertEquals(ValidationException.class, e.getClass());
			assertEquals("Field 'number' is mandatory", e.getMessage());
		}
	}

	
	@Test
	public void testFindAll() {
		
		try {
			Collection<Address> result = service.findAll();
			assertNotNull(result);
			assertTrue(result.size() > 0);
		} catch (ExcessiveLoadDataAccessException e) {
			fail("Exception not expected");
		}
	}

}
