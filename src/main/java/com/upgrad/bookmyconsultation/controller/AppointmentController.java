package com.upgrad.bookmyconsultation.controller;

import com.upgrad.bookmyconsultation.entity.Appointment;
import com.upgrad.bookmyconsultation.exception.InvalidInputException;
import com.upgrad.bookmyconsultation.exception.SlotUnavailableException;
import com.upgrad.bookmyconsultation.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

	private final AppointmentService appointmentService;

	@Autowired
	public AppointmentController(AppointmentService appointmentService) {
		this.appointmentService = appointmentService;
	}

	/**
	 * @param appointment is the appointment details
	 * @return ResponseEntity<String>
	 * @throws InvalidInputException for invalid input given in the request body
	 * @throws SlotUnavailableException if the slot is already booked with the same doctor on the same date and time
	 * created by Arsalan Ansari
	 */
	@PostMapping
	public ResponseEntity<String> bookAppointment(@RequestBody Appointment appointment) throws InvalidInputException, SlotUnavailableException {
		String appointmentId = appointmentService.appointment(appointment);
		return new ResponseEntity<>(appointmentId, HttpStatus.CREATED);
	}

	/**
	 * @param appointmentsId is the unique id for the appointment
	 * @return ResponseEntity<Appointment> to provide a response
	 * created by Arsalan Ansari
	 */
	@GetMapping(value = "/{appointmentsId}")
	public ResponseEntity<Appointment> getAppointment(@PathVariable String appointmentsId) {
		Appointment appointment = appointmentService.getAppointment(appointmentsId);
		return new ResponseEntity<>(appointment, HttpStatus.OK);
	}
}