package com.stoom.service.address;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * An Address Repository.
 * The implementation is provided by Spring Data.
 * 
 * @author Rodrigo
 *
 */

public interface AddressRepository extends JpaRepository<Address, Long> {

}
