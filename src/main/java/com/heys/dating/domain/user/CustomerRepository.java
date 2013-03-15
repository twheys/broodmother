package com.heys.dating.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.google.appengine.api.datastore.Key;

@Transactional
public interface CustomerRepository extends JpaRepository<Customer, Key> {
	public Customer findByLoginIgnoreCase(final String login);
}
