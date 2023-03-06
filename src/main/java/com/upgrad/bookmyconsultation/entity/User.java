package com.upgrad.bookmyconsultation.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity

/*
    This class is the entity class for the User details.
    created by Arsalan Ansari
 */
public class User {
    @Id
    private String emailId;
    private String firstName;
    private String lastName;
    private String dob;
    private String mobile;
    private String password;
    private String createdDate;
    private String salt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return emailId != null && Objects.equals(emailId, user.emailId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}