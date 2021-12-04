package edu.awieclawski.postgresjpa.credentials.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

	@Column(name = "password")
	@Size(min = 8, max = 20, message = "*Password size 8 - 20 characters")
	@NotEmpty(message = "*Please provide your password")
	private String password;

	@Transient
	private String passwordConfirm;

	@Column(name = "active", columnDefinition = "boolean NOT NULL DEFAULT true")
	private Boolean active;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

	@PrePersist
	public void prePersist() {
		if (this.active == null)
			this.active = Boolean.TRUE;
	}
}