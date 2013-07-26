package com.heys.dating.impl.gae.repository;

import com.heys.dating.member.Member;
import com.heys.dating.message.Thread;
import com.heys.dating.message.ThreadLeaf;
import com.heys.dating.message.ThreadLeafRepository;

public class ThreadLeafRepositoryDatastoreImpl extends
		AbstractDatastoreRepository<ThreadLeaf> implements ThreadLeafRepository {

	public ThreadLeafRepositoryDatastoreImpl() {
		super(ThreadLeaf.class);
	}

	@Override
	public Iterable<ThreadLeaf> findByOwner(final Member owner,
			final Integer limit, final Integer offset, final String sort) {
		return load().ancestor(owner).limit(limit).offset(offset).order(sort)
				.list();
	}

	@Override
	public ThreadLeaf findByOwnerAndThread(final Member owner,
			final Thread thread) {
		return load().ancestor(owner).filter("thread", thread).first().now();
	}

}
