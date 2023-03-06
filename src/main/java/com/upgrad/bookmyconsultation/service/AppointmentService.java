package com.upgrad.bookmyconsultation.service;

import com.upgrad.bookmyconsultation.controller.ext.ErrorResponse;
import com.upgrad.bookmyconsultation.controller.ext.ErrorResponseBuilder;
import com.upgrad.bookmyconsultation.entity.Appointment;
import com.upgrad.bookmyconsultation.exception.InvalidInputException;
import com.upgrad.bookmyconsultation.exception.ResourceUnAvailableException;
import com.upgrad.bookmyconsultation.exception.SlotUnavailableException;
import com.upgrad.bookmyconsultation.repository.AppointmentRepository;
import com.upgrad.bookmyconsultation.repository.UserRepository;
import com.upgrad.bookmyconsultation.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    //variable to inject the dependency object for appointment repository
    AppointmentRepository appointmentRepository;

    //variable to inject the dependency object for user repository
    UserRepository userRepository;

	@Autowired
	public AppointmentService(AppointmentRepository appointmentRepository, UserRepository userRepository) {
		this.appointmentRepository = appointmentRepository;
		this.userRepository = userRepository;
	}

    /**
     * @param newAppointment is the appointment details for the appointment that user trying to book
     * @return unique appointment id if everything works fine
     * @throws SlotUnavailableException if the slot is already booked with the same doctor on the same date and time
     * @throws InvalidInputException if the inputs entered by the user are invalid
     * created by Arsalan Ansari
     */
    public String appointment(Appointment newAppointment) throws SlotUnavailableException, InvalidInputException {
        ValidationUtils.validate(newAppointment);
        Appointment oldAppointment = appointmentRepository.findByDoctorIdAndTimeSlotAndAppointmentDate(newAppointment.getDoctorId(), newAppointment.getTimeSlot(), newAppointment.getAppointmentDate());
        if (oldAppointment != null) {
            throw new SlotUnavailableException();
        }
        else appointmentRepository.save(newAppointment);
        return newAppointment.getAppointmentId();
    }

    /**
     * @param appointmentId is the appointment id for the appointment that user wants to search
     * @return details of the appointment that corresponds to the appointment id
     * created by Arsalan Ansari
     */
    public Appointment getAppointment(String appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElse(null);
        if (appointment != null)
            return appointment;
        else throw new ResourceUnAvailableException();
    }

    public List<Appointment> getAppointmentsForUser(String userId) {
        return appointmentRepository.findByUserId(userId);
    }
}