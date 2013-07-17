package com.heys.dating.domain.repository;

import java.util.Collection;

import com.heys.dating.domain.Repository;
import com.heys.dating.domain.member.Member;
import com.heys.dating.domain.message.Thread;

public interface ThreadRepository extends Repository<Thread> {

	Collection<Thread> findByOwner(final Member owner, final Integer limit,
			final Integer offset, final String order);

}
