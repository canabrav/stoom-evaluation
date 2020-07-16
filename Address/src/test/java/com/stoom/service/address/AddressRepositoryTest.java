package com.stoom.service.address;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AddressServiceLoader.class)
public class AddressRepositoryTest {

	@Autowired
	AddressRepository repository;

	@Test
	public void testSaveNull() {

		try {
			Address address = null;
			repository.save(address);

			fail("save(null) should throw exception.");

		} catch (Exception e) {
			assertEquals(InvalidDataAccessApiUsageException.class, e.getClass());
		}
	}

	@Test
	public void testSaveInvalid() {

		try {
			repository.save(new Address());
			fail("save(empty Address) should throw exception.");

		} catch (Exception e) {
			assertEquals(DataIntegrityViolationException.class, e.getClass());
		}
	}


	@Test
	public void testValidSaveFindRemove1() {

		long countBefore = repository.count();

		Address address = repository.save(new Address("Rua dos Bobos", "1", null, "Limoeiro", "Sabará", "MG", "Brasil").withZipCode("30-000"));

		assertNotNull("Must return a not-null ID", address.getId());
		long countAfterInsert = repository.count();
		assertTrue(countAfterInsert - countBefore == 1);
		
		Address same = repository.findOne(address.getId());
		
		assertNotNull(same);
		assertEquals(address, same);
		
		repository.delete(address.getId());

		long countAfterRemove = repository.count();
		assertTrue(countAfterRemove - countBefore == 0);
		
		Address notExists = repository.findOne(address.getId());

		assertNull("Expected to be null", notExists);
	}

	@Test
	public void testValidSaveFindRemove2() {
		long countBefore = repository.count();

		Address address = repository.save(new Address("Rua dos Bobos", "1", null, "Limoeiro", "Sabará", "MG", "Brasil").withZipCode("30-000"));

		assertNotNull("Must return a not-null ID", address.getId());
		long countAfterInsert = repository.count();
		assertTrue(countAfterInsert - countBefore == 1);
		
		Address same = repository.findOne(address.getId());
		
		assertNotNull(same);
		assertEquals(address, same);
		
		repository.delete(address);

		long countAfterRemove = repository.count();
		assertTrue(countAfterRemove - countBefore == 0);
		
		Address notExists = repository.findOne(address.getId());

		assertNull("Expected to be null", notExists);
		
	}

	
	@Test
	public void testRemoveInvalidId() {
		Long invalidId = -100L;
		
		Address notExists = repository.findOne(invalidId);
		assertNull("Expected to be null", notExists);

		try {
			repository.delete(invalidId);
			fail("Should have thrown an Exception");
		} catch (Exception e) {
			assertEquals(EmptyResultDataAccessException.class, e.getClass());
		}
	}

	@Test
	public void testRemoveInvalidObject() {
		
		try {
			repository.delete(new Address().withId(-100L));
			fail("Should have thrown an Exception");
		} catch (Exception e) {
			assertEquals(DataIntegrityViolationException.class, e.getClass());
		}
	}

	
	@Test
	public void testList() {
		long count = repository.count();
		List<Address> result = repository.findAll();
		assertEquals(count, result.size());
	}

}
