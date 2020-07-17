package com.stoom.service.address;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stoom.common.Json;
import com.stoom.service.address.exception.ExcessiveLoadDataAccessException;
import com.stoom.service.address.exception.ValidationException;


@RestController
public class AddressController {

	@Autowired
	private AddressService service;
	
	@RequestMapping(value = "/address/ping", method = RequestMethod.GET)
	public ResponseEntity<Collection<Address>> ping() {
		return new ResponseEntity<Collection<Address>>(HttpStatus.OK);
	}

	
	@RequestMapping(value = "/address/all", method = RequestMethod.GET)
	public ResponseEntity<Collection<Address>> listAll() {
		
	    try {
			return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
		} catch (ExcessiveLoadDataAccessException e) {
			return new ResponseEntity<>(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE);
		}
		
	}
	
	@RequestMapping(value = "/address/{id}", method = RequestMethod.GET)
	public ResponseEntity<Address> getAddress(@PathVariable("id") String id) {
		
		if (!StringUtils.isNumeric(id)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Address address = service.findById(Long.valueOf(id));
		
		if (address == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		} else {
			return new ResponseEntity<>(address, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/address/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Address> delete(@PathVariable("id") String id) {

		if (!StringUtils.isNumeric(id)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		try {
			service.remove(Long.valueOf(id));
			return new ResponseEntity<>(HttpStatus.OK);

		} catch (EmptyResultDataAccessException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/address/", method = RequestMethod.PUT)
    public ResponseEntity<Serializable> save(@RequestBody String payload) {
		
		if (StringUtils.isBlank(payload)) {
			return new ResponseEntity<>("null payload", HttpStatus.BAD_REQUEST);
		}

		Address address;
		try {
			address = Json.parse(payload, Address.class);
			
		} catch (IOException e) {
			return new ResponseEntity<>("Invalid format", HttpStatus.BAD_REQUEST);
		}

		try {
			address = service.save(address);
			return new ResponseEntity<>(address.getId(), HttpStatus.OK);

		} catch (ValidationException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
