package com.heys.dating.domain.repository;

import java.util.Collection;

import com.googlecode.objectify.Key;
import com.heys.dating.domain.Repository;
import com.heys.dating.domain.member.Member;
import com.heys.dating.domain.message.MessageThread;

public interface MessageThreadRepository extends Repository<MessageThread> {

	MessageThread findByMemberAndSharedID(Key<Member> member, String sThreadUID);

	Collection<MessageThread> findNewForMember(Key<Member> member,
			Integer limit, Integer offset);

}
