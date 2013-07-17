package com.heys.dating.domain.repository;

import com.heys.dating.domain.Repository;
import com.heys.dating.domain.member.Member;
import com.heys.dating.domain.message.MemberMessage;
import com.heys.dating.domain.message.Thread;

public interface MemberMessageRepository extends Repository<MemberMessage> {

	Iterable<MemberMessage> findByOwnerAndThread(final Member owner,
			final Thread thread, final int limit, final int offset,
			final String sort);

}
