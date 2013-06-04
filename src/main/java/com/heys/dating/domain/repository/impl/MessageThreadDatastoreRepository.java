package com.heys.dating.domain.repository.impl;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import com.googlecode.objectify.Key;
import com.heys.dating.domain.AbstractRepository;
import com.heys.dating.domain.member.Member;
import com.heys.dating.domain.message.MessageThread;
import com.heys.dating.domain.repository.MessageThreadRepository;

@Repository
public class MessageThreadDatastoreRepository extends
		AbstractRepository<MessageThread> implements MessageThreadRepository {

	public MessageThreadDatastoreRepository() {
		super(MessageThread.class);
	}

	@Override
	public MessageThread findByMemberAndSharedID(final Key<Member> member,
			final String sThreadUID) {
		return load().filter("member", member).filter("sharedID", sThreadUID)
				.first().now();
	}

	@Override
	public Collection<MessageThread> findNewForMember(final Key<Member> member,
			final Integer limit, final Integer offset) {
		return load().ancestor(member).limit(limit).offset(offset).list();
	}

}
