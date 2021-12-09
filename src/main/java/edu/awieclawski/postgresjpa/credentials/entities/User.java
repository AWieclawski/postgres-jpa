package edu.awieclawski.postgresjpa.credentials.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_tb")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private Long id;

	@Column(name = "user_name", unique = true, columnDefinition = "varchar(20) NOT NULL UNIQUE")
	@Size(min = 6, max = 20, message = "*User name size 6 - 20 characters")
	@NotEmpty(message = "*Please provide a nique user name")
	private String username;

	@Transient
	@JsonIgnore
	@ToString.Exclude
	@NotEmpty(message = "*Please provide your password")
	@Size(min = 8, max = 20, message = "*Password size 8 - 20 characters")
	private String password;

	@JsonIgnore
	@ToString.Exclude
	@Column(name = "password", nullable = false, updatable = true)
	private String passCrypt;;

	@Transient
	@ToString.Exclude
	private String passwordConfirm;

	@Transient
	@ToString.Exclude
	private Boolean userExists;

	@Column(name = "active", columnDefinition = "boolean NOT NULL DEFAULT true")
	private Boolean active;

	@OneToMany(mappedBy = "user")
	@ToString.Exclude
	Set<UserRegistration> registrations;

	public void addUserRegistration(UserRegistration registration) {
		if (registrations == null)
			registrations = new HashSet<>();
		this.registrations.add(registration);
		registration.setUser(this);
	}

	public void removeUserRegistration(UserRegistration registration) {
		this.registrations.remove(registration);
		registration.setUser(null);
	}

	@PreUpdate
	@PrePersist
	public void prePersist() {
		if (this.active == null)
			this.active = Boolean.TRUE;
	}

}
