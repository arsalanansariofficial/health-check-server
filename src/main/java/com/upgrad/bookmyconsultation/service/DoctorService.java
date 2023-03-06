package com.upgrad.bookmyconsultation.service;

import com.upgrad.bookmyconsultation.entity.Address;
import com.upgrad.bookmyconsultation.entity.Doctor;
import com.upgrad.bookmyconsultation.enums.Speciality;
import com.upgrad.bookmyconsultation.exception.InvalidInputException;
import com.upgrad.bookmyconsultation.exception.ResourceUnAvailableException;
import com.upgrad.bookmyconsultation.model.TimeSlot;
import com.upgrad.bookmyconsultation.repository.AddressRepository;
import com.upgrad.bookmyconsultation.repository.AppointmentRepository;
import com.upgrad.bookmyconsultation.repository.DoctorRepository;
import com.upgrad.bookmyconsultation.util.ValidationUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.annotations.Cacheable;

import javax.print.Doc;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
public class DoctorService {
    AppointmentRepository appointmentRepository;
    DoctorRepository doctorRepository;
    AddressRepository addressRepository;

    @Autowired
    public DoctorService(AppointmentRepository appointmentRepository, DoctorRepository doctorRepository, AddressRepository addressRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.addressRepository = addressRepository;
    }

    /**
     * @param newDoctor is the details of the new doctor
     * @return the object of type Doctor with the details of the new doctor
     * @throws InvalidInputException if the inputs are invalid
     */
    public Doctor register(Doctor newDoctor) throws InvalidInputException {

        ValidationUtils.validate(newDoctor);
        newDoctor.setId(UUID.randomUUID().toString());

        if (newDoctor.getAddress() == null) {
            List<String> attributes = new ArrayList<>();
            attributes.add("Address can't be left empty");
            throw new InvalidInputException(attributes);
        }
        newDoctor.getAddress().setId(UUID.randomUUID().toString());

        if (newDoctor.getSpeciality() == null)
            newDoctor.setSpeciality(Speciality.GENERAL_PHYSICIAN);

        Address doctorsAddress = newDoctor.getAddress();
        doctorsAddress = addressRepository.save(doctorsAddress);

        newDoctor.setAddress(doctorsAddress);
        newDoctor = doctorRepository.save(newDoctor);
        return newDoctor;
    }

    /**
     * @param id the unique id of the doctor
     * @return the object of type Doctor
     * created by Arsalan Ansari
     */
    public Doctor getDoctor(String id) {
        Doctor doctor = doctorRepository.findById(id).orElse(null);
        if (doctor != null)
            return doctor;
        else throw new ResourceUnAvailableException();
    }

    public List<Doctor> getAllDoctorsWithFilters(String speciality) {

        if (speciality != null && !speciality.isEmpty()) {
            return doctorRepository.findBySpecialityOrderByRatingDesc(Speciality.valueOf(speciality));
        }
        return getActiveDoctorsSortedByRating();
    }

    @Cacheable(value = "doctorListByRating")
    private List<Doctor> getActiveDoctorsSortedByRating() {
        log.info("Fetching doctor list from the database");
        return doctorRepository.findAllByOrderByRatingDesc().stream().limit(20).collect(Collectors.toList());
    }

    public TimeSlot getTimeSlots(String doctorId, String date) {

        TimeSlot timeSlot = new TimeSlot(doctorId, date);
        timeSlot.setTimeSlot(timeSlot.getTimeSlot()
                .stream()
                .filter(slot -> appointmentRepository
						.findByDoctorIdAndTimeSlotAndAppointmentDate(timeSlot.getDoctorId(), slot, timeSlot.getAvailableDate()) == null)
                .collect(Collectors.toList()));

        return timeSlot;
    }
}