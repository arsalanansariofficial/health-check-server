package com.upgrad.bookmyconsultation.controller;

import com.upgrad.bookmyconsultation.entity.Rating;
import com.upgrad.bookmyconsultation.service.RatingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RatingsController {

	private final RatingsService ratingsService;

	@Autowired
	public RatingsController(RatingsService ratingsService) {
		this.ratingsService = ratingsService;
	}

	/**
	 * @param rating is the rating that user wants to submit for the current appointment
	 * @return ResponseEntity<String> for the response
	 * created by Arsalan Ansari
	 */
	@PostMapping(value = "/ratings")
	public ResponseEntity<String> submitRatings(@RequestBody Rating rating) {
		ratingsService.submitRatings(rating);
		return new ResponseEntity<>("Rating successfully submitted!", HttpStatus.OK);
	}
}