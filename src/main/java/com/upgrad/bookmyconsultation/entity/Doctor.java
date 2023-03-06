package com.upgrad.bookmyconsultation.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.upgrad.bookmyconsultation.enums.Speciality;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class Doctor {
    @Id
    private String id = UUID.randomUUID().toString();
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Speciality speciality;
    private String dob;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Address address;
    private String mobile;
    private String emailId;
    private String pan;
    private String highestQualification;
    private String college;
    private Integer totalYearsOfExp;
    private Double rating;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Doctor doctor = (Doctor) o;
        return id != null && Objects.equals(id, doctor.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}