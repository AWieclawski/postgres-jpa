package edu.awieclawski.postgresjpa.controllers;

import java.util.List;

import org.javers.core.Changes;
import org.javers.core.Javers;
import org.javers.repository.jql.QueryBuilder;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.javers.repository.jql.JqlQuery;
import org.javers.shadow.Shadow;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.awieclawski.postgresjpa.entities.Customer;
import edu.awieclawski.postgresjpa.repositories.CustomerRepository;

/**
 * The simple controller for dispatching changes into our application and
 * retrieving the JaVers commit log, according to:
 * 
 * https://www.baeldung.com/spring-data-javers-audit
 * 
 * @author AWieclawski
 *
 */
@RestController
@RequestMapping("/changes")
public class CustomersChangesController {

	private final Javers javers;
	private final CustomerRepository customerRepository;

	public CustomersChangesController(Javers javers, CustomerRepository customerRepository) {
		this.javers = javers;
		this.customerRepository = customerRepository;
	}

	@GetMapping("/customers/{id}")
	public String getCustomerChanges(@PathVariable Long id) {
		Customer customer = customerRepository.findById(id).get();
		QueryBuilder jqlQuery = QueryBuilder.byInstance(customer);
		Changes changes = javers.findChanges(jqlQuery.build());
		return javers.getJsonConverter().toJson(changes);
	}

	@GetMapping("/customers/snapshots")
	public String getProductSnapshots() {
		QueryBuilder jqlQuery = QueryBuilder.byClass(Customer.class);
		List<CdoSnapshot> snapshots = javers.findSnapshots(jqlQuery.build());
		return javers.getJsonConverter().toJson(snapshots);
	}

	@GetMapping("/customers/shadows/{id}")
	public String getStoreShadows(@PathVariable Long id) {
		Customer customer = customerRepository.findById(id).get();
		JqlQuery jqlQuery = QueryBuilder.byInstance(customer).withChildValueObjects().build();
		List<Shadow<Customer>> shadows = javers.findShadows(jqlQuery);
		return javers.getJsonConverter().toJson(shadows.get(0));
	}

}
