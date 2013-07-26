package com.heys.dating.message;

import java.util.Collection;

import com.heys.dating.Repository;
import com.heys.dating.member.Member;

public interface ThreadRepository extends Repository<Thread> {

	Collection<Thread> findByOwner(final Member owner, final Integer limit,
			final Integer offset, final String order);

}
