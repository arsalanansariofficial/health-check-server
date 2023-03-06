package com.upgrad.bookmyconsultation.service;

import com.upgrad.bookmyconsultation.entity.Doctor;
import com.upgrad.bookmyconsultation.entity.Rating;
import com.upgrad.bookmyconsultation.repository.DoctorRepository;
import com.upgrad.bookmyconsultation.repository.RatingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class RatingsService {
    ApplicationEventPublisher publisher;
    RatingsRepository ratingsRepository;
    DoctorRepository doctorRepository;

    @Autowired
    public RatingsService(ApplicationEventPublisher publisher, RatingsRepository ratingsRepository, DoctorRepository doctorRepository) {
        this.publisher = publisher;
        this.ratingsRepository = ratingsRepository;
        this.doctorRepository = doctorRepository;
    }

    /**
     * @param rating is the rating that user wants to give to the appointment
       after the appointment is complete
     * created by Arsalan Ansari
     */
    public void submitRatings(Rating rating) {

        rating.setId(UUID.randomUUID().toString());
        ratingsRepository.save(rating);

        String doctorId = rating.getDoctorId();
        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);

        if (doctor != null) {
            Double oldRating = doctor.getRating();
            Integer newRating = rating.getRating();
            Double averageRating = (oldRating + newRating) / 2;

            doctor.setRating(averageRating);
            doctorRepository.save(doctor);
        }
    }
}