package com.stoom.service.address;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.stoom.service.address.exception.ExcessiveLoadDataAccessException;
import com.stoom.service.address.exception.ValidationException;

/**
 * This class provides the Service Layer (transactional layer) for the Address microservice.
 * @author Rodrigo
 *
 */
@Component
@Transactional
public class AddressService {

	private final AddressRepository repository;

	@Autowired
	public AddressService(AddressRepository repository) {
		super();
		this.repository = repository;
	}
	
	public Address save(Address address) throws ValidationException {
		
		Assert.notNull(address, "address must not be null");
		
		address.validate();
		
		return repository.save(address);

	}

	public Address findById(Long id) {
		
		Assert.notNull(id, "id must not be null");

		return repository.findOne(id);

	}

	public Collection<Address> findAll() throws ExcessiveLoadDataAccessException {
		if (repository.count() > 500) {
			throw new ExcessiveLoadDataAccessException();
		}
		return repository.findAll();

	}

	public void remove(Long id) {
		
		Assert.notNull(id, "id must not be null");
		
		repository.delete(id);

	}
}
