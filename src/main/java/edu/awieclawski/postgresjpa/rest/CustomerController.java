package edu.awieclawski.postgresjpa.rest;

import org.springframework.web.bind.annotation.*;

import edu.awieclawski.postgresjpa.dto.CustomerData;
import edu.awieclawski.postgresjpa.services.CustomerService;
import edu.awieclawski.postgresjpa.config.CtrlrPaths;

import javax.annotation.Resource;

@RestController
@RequestMapping(CtrlrPaths.API_CUSTOMERS)
public class CustomerController {

	@Resource(name = "customerService")
	private CustomerService customerService;

	/**
	 * Get all customer data in the system.For production system you many want to
	 * use pagination.
	 * 
	 * @return List<CustomerData>
	 * 
	 *         ex. http://localhost:8080/customers/
	 */
	@GetMapping
	public Iterable<CustomerData> getCustomers() {
		return customerService.getAllCustomers();
	}

	/**
	 * Method to get the customer data based on the ID.
	 * 
	 * @param id
	 * @return CustomerData
	 * 
	 *         ex. http://localhost:8080/customers/customer/1
	 */
	@GetMapping("/customer/{id}")
	public CustomerData getCustomer(@PathVariable Long id) {
		return customerService.getCustomerById(id);
	}

	/**
	 * Post request to create customer information int the system.
	 * 
	 * @param customerData
	 * @return
	 * 
	 */
	@PostMapping("/customer")
	public CustomerData saveCustomer(final @RequestBody CustomerData customerData) {
		return customerService.saveCustomer(customerData);
	}

	/**
	 * Delete customer from the system based on the ID. The method mapping is
	 * similar to the getCustomer with difference of
	 * 
	 * @DeleteMapping and @GetMapping
	 * @param id
	 * @return Boolean
	 * 
	 */
	@DeleteMapping("/customer/{id}")
	public Boolean deleteCustomer(@PathVariable Long id) {
		return customerService.deleteCustomer(id);
	}

}
