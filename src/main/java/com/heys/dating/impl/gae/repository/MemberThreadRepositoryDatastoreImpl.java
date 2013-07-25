package com.heys.dating.impl.gae.repository;

import com.heys.dating.member.Member;
import com.heys.dating.message.MemberThread;
import com.heys.dating.message.MemberThreadRepository;
import com.heys.dating.message.Thread;

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
