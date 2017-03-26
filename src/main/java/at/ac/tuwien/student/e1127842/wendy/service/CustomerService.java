package at.ac.tuwien.student.e1127842.wendy.service;

import at.ac.tuwien.student.e1127842.wendy.domain.Customer;
import at.ac.tuwien.student.e1127842.wendy.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerService {
	private final CustomerRepository repository;

	@Autowired
	public CustomerService(CustomerRepository repository) {
		this.repository = repository;
	}

	public Customer getById(UUID customerId) {
		return repository.getOne(customerId);
	}

	public void save(Customer customer) {
		repository.save(customer);
	}
}
