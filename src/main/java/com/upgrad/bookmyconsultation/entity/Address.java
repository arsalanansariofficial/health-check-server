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
public class Address {
	@Id
	private String id;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String postcode;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Address address = (Address) o;
		return id != null && Objects.equals(id, address.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}