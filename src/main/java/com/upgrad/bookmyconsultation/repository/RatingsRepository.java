package com.upgrad.bookmyconsultation.repository;

import com.upgrad.bookmyconsultation.entity.Rating;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
    This is the interface for the RatingsRepository that provides some
    basic methods of the CrudRepository
    created by Arsalan Ansari
 */

@Repository
public interface RatingsRepository extends CrudRepository<Rating, String> {
    List<Rating> findByDoctorId(String doctorId);
}