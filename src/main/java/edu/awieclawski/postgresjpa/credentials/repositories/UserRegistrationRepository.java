package edu.awieclawski.postgresjpa.credentials.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.awieclawski.postgresjpa.credentials.entities.UserRegistration;

public interface UserRegistrationRepository extends JpaRepository<UserRegistration, Long> {

}
