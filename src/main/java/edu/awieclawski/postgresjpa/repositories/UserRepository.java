package edu.awieclawski.postgresjpa.repositories;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.awieclawski.postgresjpa.entities.Customer;
import edu.awieclawski.postgresjpa.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

	User findByUserName(String userName);

	Optional<User> findById(Long id);

	@Query("SELECT u FROM User u WHERE u.active = true")
	Collection<Customer> findAllActiveUsers();

	@Query("SELECT u FROM User u WHERE u.active = true AND u.id = : id")
	Collection<Customer> findOneActiveUser(@Param("id") Long id);
}