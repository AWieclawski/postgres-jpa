package edu.awieclawski.postgresjpa.credentials.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "registrations_tb")
public class UserRegistration {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "reg_id")
	Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	User user;

	@ManyToOne
	@JoinColumn(name = "role_id")
	Role role;

	@Column(name = "registration", updatable = false)
	LocalDateTime registeredAt;

	@PrePersist
	private void init() {
		if (this.registeredAt == null)
			this.registeredAt = LocalDateTime.now();
	}

	@Override
	public String toString() {
		return "UserRegistration [id=" + id + ", registeredAt=" + registeredAt + ", user="
				+ (getUser() != null ? user.getId() : null) + ", role=" + (getRole() != null ? role.getId() : null)
				+ "]";
	}

}
