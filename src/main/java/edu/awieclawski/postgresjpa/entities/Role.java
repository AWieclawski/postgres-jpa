package edu.awieclawski.postgresjpa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import edu.awieclawski.postgresjpa.config.AppRoles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "role_id")
	private int id;

	@Column(name = "role_name", columnDefinition = "varchar(20) NOT NULL DEFAULT 'DEFAULT'")
	private String role;

	@PrePersist
	public void prePersist() {
		if (this.role == null)
			this.role = AppRoles.USER.getRoleName();
	}
}
