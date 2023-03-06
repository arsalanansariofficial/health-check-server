package com.upgrad.bookmyconsultation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class Appointment {
	@Id
	private String appointmentId = UUID.randomUUID().toString();
	private String doctorId;
	private String doctorName;
	private String userId;
	private String userName;
	private String userEmailId;
	private String timeSlot;
	private String status;
	private String appointmentDate;
	@JsonIgnore
	private String createdDate;
	private String symptoms;
	private String priorMedicalHistory;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Appointment that = (Appointment) o;
		return appointmentId != null && Objects.equals(appointmentId, that.appointmentId);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}