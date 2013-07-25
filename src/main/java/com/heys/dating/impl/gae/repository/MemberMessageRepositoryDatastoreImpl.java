package com.heys.dating.impl.gae.repository;

import org.springframework.stereotype.Repository;

import com.heys.dating.member.Member;
import com.heys.dating.message.MemberMessage;
import com.heys.dating.message.MemberMessageRepository;
import com.heys.dating.message.Thread;

@Repository
public class MemberMessageRepositoryDatastoreImpl extends
		AbstractDatastoreRepository<MemberMessage> implements
		MemberMessageRepository {

	public MemberMessageRepositoryDatastoreImpl() {
		super(MemberMessage.class);
	}

	@Override
	public Iterable<MemberMessage> findByOwnerAndThread(final Member owner,
			final Thread thread, final int limit, final int offset,
			final String sort) {
		return load().ancestor(owner).filter("thread", thread).limit(limit)
				.offset(offset).order(sort).list();

	}
}
