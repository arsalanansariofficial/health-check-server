package com.upgrad.bookmyconsultation.entity;

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
@Entity

/*
  This class is the entity class for the Rating that user puts
  after the appointment is complete
  created by Arsalan Ansari
 */
public class Rating {
    @Id
    private String id = UUID.randomUUID().toString();
    private String appointmentId;
    private String doctorId;
    private Integer rating;
    private String comments;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Rating rating = (Rating) o;
        return id != null && Objects.equals(id, rating.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}