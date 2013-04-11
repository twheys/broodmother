package com.heys.dating.security;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.heys.dating.domain.repository.CustomerRepository;
import com.heys.dating.domain.user.Customer;

@Component
public class DatastoreUserService implements UserDetailsService {
	private static final Logger logger = Logger
			.getLogger(DatastoreUserService.class.getName());

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public UserDetails loadUserByUsername(final String identifier)
			throws UsernameNotFoundException {
		logger.info("Attempting to authorize " + identifier);
		Customer customer = null;
		customer = customerRepository.findByLoginOrEmailIgnoreCase(identifier);
		if (null == customer) {
			logger.info("User does not exist " + identifier);
			throw new UsernameNotFoundException(identifier);
		}
		return new DatingUserDetails(customer);
	}

}
