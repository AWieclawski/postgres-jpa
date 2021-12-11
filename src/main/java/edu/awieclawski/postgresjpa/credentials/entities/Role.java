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
@Table(name = "role_tb")
public class Role implements I_Role {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "role_id")
	private Long id;

	@Column(name = "role_name", columnDefinition = "varchar(20) NOT NULL DEFAULT 'DEFAULT'")
	private String name;

	@OneToMany(mappedBy = "role")
	@ToString.Exclude
	Set<UserRegistration> registrations;

	@PreUpdate
	@PrePersist
	public void prePersist() {
		if (this.name == null)
			this.name = DEFAULT_ROLENAME;
	}

	public void addUserRegistration(UserRegistration registration) {
		if (registrations == null)
			registrations = new HashSet<>();
		this.registrations.add(registration);
		if (registration != null)
			registration.setRole(this);
	}

	public void removeUserRegistration(UserRegistration registration) {
		this.registrations.remove(registration);
		registration.setRole(null);
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", registrations="
				+ (registrations != null && registrations.size() > 0 ? registrations : null) + "]";
	}

}
