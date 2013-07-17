package com.heys.dating.domain.repository.impl;

import com.heys.dating.domain.AbstractDatastoreRepository;
import com.heys.dating.domain.member.Member;
import com.heys.dating.domain.message.MemberThread;
import com.heys.dating.domain.message.Thread;
import com.heys.dating.domain.repository.MemberThreadRepository;

public class MemberThreadRepositoryDatastoreImpl extends
		AbstractDatastoreRepository<MemberThread> implements
		MemberThreadRepository {

	public MemberThreadRepositoryDatastoreImpl() {
		super(MemberThread.class);
	}

	@Override
	public Iterable<MemberThread> findByOwner(final Member owner,
			final Integer limit, final Integer offset, final String sort) {
		return load().ancestor(owner).limit(limit).offset(offset).order(sort)
				.list();
	}

	@Override
	public MemberThread findByOwnerAndThread(final Member owner,
			final Thread thread) {
		return load().ancestor(owner).filter("thread", thread).first().now();
	}

}
