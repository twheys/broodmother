package com.heys.dating.domain.repository.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.heys.dating.domain.AbstractRepository;
import com.heys.dating.domain.repository.CustomerRepository;
import com.heys.dating.domain.user.Customer;

@Repository
@Transactional
public class CustomerRepositoryImpl extends AbstractRepository<Customer>
		implements CustomerRepository {

	public CustomerRepositoryImpl() {
		super(Customer.class);
	}

	@Override
	public Customer findByEmailIgnoreCase(final String email) {
		return getSingleResult(entityManager.createQuery(
				"select c from Customer c where c.email = :email", type)
				.setParameter("email", email));
	}

	@Override
	public Customer findByLoginIgnoreCase(final String login) {
		return getSingleResult(entityManager.createQuery(
				"select c from Customer c where c.login = :login", type)
				.setParameter("login", login));
	}

	/**
	 * GAE Datastore doesn't support OR clauses in queries, therefore two
	 * queries are performed.
	 */
	@Override
	public Customer findByLoginOrEmailIgnoreCase(final String identifier) {
		Customer customer;
		if (null != (customer = findByLoginIgnoreCase(identifier))) {
			return customer;
		}

		if (null != (customer = findByEmailIgnoreCase(identifier))) {
			return customer;
		}

		return null;
	}
}
