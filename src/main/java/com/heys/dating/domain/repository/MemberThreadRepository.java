package com.heys.dating.domain.repository;

import com.heys.dating.domain.Repository;
import com.heys.dating.domain.member.Member;
import com.heys.dating.domain.message.MemberThread;
import com.heys.dating.domain.message.Thread;

public interface MemberThreadRepository extends Repository<MemberThread> {

	MemberThread findByOwnerAndThread(final Member owner, final Thread thread);

	Iterable<MemberThread> findByOwner(final Member owner, final Integer limit,
			final Integer offset, final String sort);
}
