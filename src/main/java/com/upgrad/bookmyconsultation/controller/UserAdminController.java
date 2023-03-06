package com.upgrad.bookmyconsultation.controller;

import com.upgrad.bookmyconsultation.entity.Appointment;
import com.upgrad.bookmyconsultation.entity.User;
import com.upgrad.bookmyconsultation.exception.InvalidInputException;
import com.upgrad.bookmyconsultation.service.AppointmentService;
import com.upgrad.bookmyconsultation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserAdminController {

    private final UserService userService;
    private final AppointmentService appointmentService;

    @Autowired
    public UserAdminController(AppointmentService appointmentService, UserService userService) {
        this.appointmentService = appointmentService;
        this.userService = userService;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<User> getUser(@RequestHeader("authorization") String accessToken, @PathVariable("id") final String userUuid) {
        final User User = userService.getUser(userUuid);
        return ResponseEntity.ok(User);
    }

    /**
     * @param user is request body of type user that provides the details of the new user
     * @return ResponseEntity<User> for the response
     * @throws InvalidInputException if request contains invalid inputs
     * created by Arsalan Ansari
     */
    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody final User user) throws  InvalidInputException {
        User newUser = userService.register(user);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @GetMapping("/{userId}/appointments")
    public ResponseEntity<List<Appointment>> getAppointmentForUser(@PathVariable("userId") String userId) {
        List<Appointment> registeredAppointments = appointmentService.getAppointmentsForUser(userId);
        return new ResponseEntity<>(registeredAppointments, HttpStatus.OK);
    }
}