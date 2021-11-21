package edu.awieclawski.postgresjpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.awieclawski.postgresjpa.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	Role findByRole(String role);

}
