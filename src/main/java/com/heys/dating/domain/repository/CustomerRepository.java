package com.heys.dating.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.google.appengine.api.datastore.Key;
import com.heys.dating.domain.user.Customer;

@Transactional
public interface CustomerRepository extends JpaRepository<Customer, Key> {
	Customer findByEmailIgnoreCase(String email);

	Customer findByLoginIgnoreCase(String login);

	Customer findByLoginOrEmailIgnoreCase(String value);
}
