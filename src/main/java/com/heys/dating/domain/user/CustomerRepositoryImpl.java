package com.heys.dating.domain.user;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.heys.dating.domain.AbstractRepository;

@Repository
@Transactional
public class CustomerRepositoryImpl extends AbstractRepository<Customer>
		implements CustomerRepository {
	public CustomerRepositoryImpl() {
		super(Customer.class);
	}

	@Override
	public Customer findByLoginIgnoreCase(final String login) {
		return entityManager
				.createQuery("from Customer where login = :login", type)
				.setParameter("login", login).getSingleResult();
	}
}
