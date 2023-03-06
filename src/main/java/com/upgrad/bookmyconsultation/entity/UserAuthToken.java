package com.upgrad.bookmyconsultation.entity;
import lombok.*;
import org.hibernate.Hibernate;
import javax.persistence.*;
import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class UserAuthToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;

	@Column(name = "ACCESS_TOKEN", length = 1000)
	private String accessToken;

	private ZonedDateTime loginAt;

	private ZonedDateTime expiresAt;

	private ZonedDateTime logoutAt;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o != null) {
			Hibernate.getClass(this);
			Hibernate.getClass(o);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}