package edu.awieclawski.postgresjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.awieclawski.postgresjpa.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
