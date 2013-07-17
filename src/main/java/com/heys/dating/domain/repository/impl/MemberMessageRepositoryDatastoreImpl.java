package com.heys.dating.domain.repository.impl;

import org.springframework.stereotype.Repository;

import com.heys.dating.domain.AbstractDatastoreRepository;
import com.heys.dating.domain.member.Member;
import com.heys.dating.domain.message.MemberMessage;
import com.heys.dating.domain.message.Thread;
import com.heys.dating.domain.repository.MemberMessageRepository;

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
