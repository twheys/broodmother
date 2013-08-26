package com.heys.dating.impl.repository;

import org.springframework.stereotype.Repository;

import com.heys.dating.member.Member;
import com.heys.dating.message.MessageLeaf;
import com.heys.dating.message.MessageLeafRepository;
import com.heys.dating.message.Thread;

@Repository
public class MessageLeafRepositoryDatastoreImpl extends
		AbstractDatastoreRepository<MessageLeaf> implements
		MessageLeafRepository {

	public MessageLeafRepositoryDatastoreImpl() {
		super(MessageLeaf.class);
	}

	@Override
	public int countUnreadByOwner(final Member owner) {
		return load().ancestor(owner).filter("readTimestamp", null).count();
	}

	@Override
	public Iterable<MessageLeaf> findByOwnerAndThread(final Member owner,
			final Thread thread, final int limit, final int offset,
			final String sort) {
		return load().ancestor(owner).filter("thread", thread).limit(limit)
				.offset(offset).order(sort).list();

	}
}
