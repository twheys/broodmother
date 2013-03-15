package com.heys.dating.service.impl;

import java.util.Date;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heys.dating.domain.user.Customer;
import com.heys.dating.domain.user.CustomerRepository;
import com.heys.dating.domain.user.Profile;
import com.heys.dating.service.CustomerManager;

@Service("CustomerManager")
public class CustomerManagerImpl implements CustomerManager {
	private static final Logger logger = Logger
			.getLogger(CustomerManagerImpl.class.getName());

	@Autowired
	private CustomerRepository customerRepository;

	private Customer createCustomer(final String login, final String password,
			final String email, final Date birthdate) {
		logger.fine("Registering new customer: " + login);
		final Customer customer = new Customer();
		customer.setLogin(login);
		customer.setPassword(password);
		customer.setEmail(email);
		customer.setBirthdate(birthdate);
		customer.setProfile(new Profile());
		return customer;
	}

	@Override
	public Customer register(final String login, final String password,
			final String email) {
		return customerRepository.save(createCustomer(login, password, email,
				null));
	}

	@Override
	public Customer register(final String login, final String password,
			final String email, final Date birthdate) {
		return customerRepository.save(createCustomer(login, password, email,
				birthdate));
	}
}
