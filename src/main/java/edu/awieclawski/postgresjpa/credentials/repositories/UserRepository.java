package edu.awieclawski.postgresjpa.credentials.repositories;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.awieclawski.postgresjpa.credentials.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

	@Query("SELECT u FROM User u WHERE u.active = true")
	Collection<User> findAllActiveUsers();

	@Query("SELECT u FROM User u WHERE u.active = true AND u.id = : id")
	Collection<User> findOneActiveUser(@Param("id") Long id);
}