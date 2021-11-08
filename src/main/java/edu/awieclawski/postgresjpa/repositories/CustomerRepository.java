package edu.awieclawski.postgresjpa.repositories;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.awieclawski.postgresjpa.entities.Customer;

@Repository
@JaversSpringDataAuditable
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
