package com.heys.dating.message;

import com.heys.dating.Repository;
import com.heys.dating.member.Member;

public interface MemberThreadRepository extends Repository<MemberThread> {

	MemberThread findByOwnerAndThread(final Member owner, final Thread thread);

	Iterable<MemberThread> findByOwner(final Member owner, final Integer limit,
			final Integer offset, final String sort);
}
