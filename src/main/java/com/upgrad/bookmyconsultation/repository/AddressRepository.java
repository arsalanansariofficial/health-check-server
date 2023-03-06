package com.upgrad.bookmyconsultation.repository;

import com.upgrad.bookmyconsultation.entity.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/*
    This is Repository interface that provides some basic methods
    of the CrudRepository
    created by Arsalan Ansari
 */

@Repository
public interface AddressRepository extends CrudRepository<Address, String> {
}