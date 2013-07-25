package com.heys.dating.message;

import com.heys.dating.Repository;
import com.heys.dating.member.Member;

public interface MemberMessageRepository extends Repository<MemberMessage> {

	Iterable<MemberMessage> findByOwnerAndThread(final Member owner,
			final Thread thread, final int limit, final int offset,
			final String sort);

}
