package edu.awieclawski.postgresjpa.repositories;

import java.util.Collection;
import java.util.Optional;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.awieclawski.postgresjpa.entities.Customer;

@Repository
@JaversSpringDataAuditable
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	@Query("SELECT c FROM Customer c WHERE c.isDeleted = false")
	Collection<Customer> findAllActiveCustomers();

	@Query("SELECT c FROM Customer c WHERE c.isDeleted = false AND c.id = : id")
	Optional<Customer> findOneActiveCustomer(@Param("id") Long id);

	@Modifying
	@Query("UPDATE Customer c SET c.isDeleted = true WHERE c.id = : id")
	int updateCustomerIsDeleted(@Param("id") Long id);

}
