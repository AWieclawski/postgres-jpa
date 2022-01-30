package edu.awieclawski.postgresjpa.repositories;

import java.util.Collection;
import java.util.Optional;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.awieclawski.postgresjpa.entities.SubCustomer;

@Repository
@JaversSpringDataAuditable
public interface SubCustomerRepository extends JpaRepository<SubCustomer, Long> {

	@Query("SELECT sub FROM SubCustomer sub WHERE sub.isDeleted = false")
	Collection<SubCustomer> findAllActiveCustomers();

	@Query("SELECT sub FROM SubCustomer sub WHERE sub.isDeleted = false AND sub.id = :id")
	Optional<SubCustomer> findOneActiveCustomer(@Param("id") Long id);

	@Modifying
	@Query("UPDATE SubCustomer sub SET sub.isDeleted = true WHERE sub.id = :id")
	int updateCustomerIsDeleted(@Param("id") Long id);

	@Query("SELECT sub FROM SubCustomer sub JOIN sub.customer cm WHERE cm.id = :id")
	Collection<SubCustomer> findSubCustomersByCustomerId(@Param("id") Long id);

}
