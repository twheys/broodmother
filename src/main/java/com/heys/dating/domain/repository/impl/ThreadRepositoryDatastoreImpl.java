package com.heys.dating.domain.repository.impl;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import com.heys.dating.domain.AbstractDatastoreRepository;
import com.heys.dating.domain.member.Member;
import com.heys.dating.domain.message.Thread;
import com.heys.dating.domain.repository.ThreadRepository;

@Repository
public class ThreadRepositoryDatastoreImpl extends
		AbstractDatastoreRepository<Thread> implements ThreadRepository {

	public ThreadRepositoryDatastoreImpl() {
		super(Thread.class);
	}

	@Override
	public Collection<Thread> findByOwner(final Member owner,
			final Integer limit, final Integer offset, final String order) {
		return load().ancestor(owner).limit(limit).offset(offset).list();
	}
}
