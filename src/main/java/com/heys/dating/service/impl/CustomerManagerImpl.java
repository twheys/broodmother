package com.heys.dating.service.impl;

import java.util.Date;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.heys.dating.domain.repository.CustomerRepository;
import com.heys.dating.domain.user.Customer;
import com.heys.dating.security.DatingUserDetails;
import com.heys.dating.service.CustomerManager;

@Service("CustomerManager")
public class CustomerManagerImpl implements CustomerManager {
	private static final Logger logger = Logger
			.getLogger(CustomerManagerImpl.class.getName());

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private SaltSource saltSource;

	private Customer createNewCustomer(final String login,
			final String password, final String email, final Date birthdate) {
		logger.fine("Registering new customer: " + login);
		final Customer customer = new Customer(login, email, birthdate);

		final DatingUserDetails principle = new DatingUserDetails(customer);
		final Object salt = saltSource.getSalt(principle);
		customer.setPassword(passwordEncoder.encodePassword(password, salt));
		return customerRepository.save(customer);
	}

	@Override
	public Customer register(final String login, final String password,
			final String email) {
		return createNewCustomer(login, password, email, null);
	}

	@Override
	public Customer register(final String login, final String password,
			final String email, final Date birthdate) {
		return createNewCustomer(login, password, email, birthdate);
	}
}
