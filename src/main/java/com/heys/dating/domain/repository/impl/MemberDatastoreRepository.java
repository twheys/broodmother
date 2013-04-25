package com.heys.dating.domain.repository.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.heys.dating.domain.AbstractRepository;
import com.heys.dating.domain.repository.MemberRepository;
import com.heys.dating.domain.user.Member;

@Repository
@Transactional
public class MemberDatastoreRepository extends AbstractRepository<Member>
		implements MemberRepository {

	public MemberDatastoreRepository() {
		super(Member.class);
	}

	@Override
	public Member findByEmailIgnoreCase(final String email) {
		return getSingleResult(entityManager.createQuery(
				"select c from Member c where c.email = :email", type)
				.setParameter("email", email));
	}

	@Override
	public Member findByLogin(final String login) {
		return getSingleResult(entityManager.createQuery(
				"select c from Member c where c.login = :login", type)
				.setParameter("login", login));
	}

	@Override
	public Member findByLoginIgnoreCase(final String login) {
		return getSingleResult(entityManager.createQuery(
				"select c from Member c where c.login = :login", type)
				.setParameter("login", login));
	}

	/**
	 * GAE Datastore doesn't support OR clauses in queries, therefore two
	 * queries are performed.
	 */
	@Override
	public Member findByLoginOrEmailIgnoreCase(final String identifier) {
		Member customer;
		if (null != (customer = findByLoginIgnoreCase(identifier)))
			return customer;

		if (null != (customer = findByEmailIgnoreCase(identifier)))
			return customer;

		return null;
	}
}
