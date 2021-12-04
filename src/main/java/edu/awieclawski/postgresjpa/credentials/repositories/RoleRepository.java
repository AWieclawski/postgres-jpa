package edu.awieclawski.postgresjpa.credentials.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.awieclawski.postgresjpa.credentials.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
