package com.heys.dating.message;

import com.heys.dating.Repository;
import com.heys.dating.member.Member;

public interface MessageLeafRepository extends Repository<MessageLeaf> {

	Iterable<MessageLeaf> findByOwnerAndThread(final Member owner,
			final Thread thread, final int limit, final int offset,
			final String sort);

	int countUnreadByOwner(final Member member);

}
