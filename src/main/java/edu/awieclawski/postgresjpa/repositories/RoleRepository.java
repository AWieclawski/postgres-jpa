package edu.awieclawski.postgresjpa.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.awieclawski.postgresjpa.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	Role findByRole(String role);

	@Query("SELECT r FROM Role r")
	Collection<Role> findAllRoles();

}
