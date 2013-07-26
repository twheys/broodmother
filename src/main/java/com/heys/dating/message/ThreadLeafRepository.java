package com.heys.dating.message;

import com.heys.dating.Repository;
import com.heys.dating.member.Member;

public interface ThreadLeafRepository extends Repository<ThreadLeaf> {

	ThreadLeaf findByOwnerAndThread(final Member owner, final Thread thread);

	Iterable<ThreadLeaf> findByOwner(final Member owner, final Integer limit,
			final Integer offset, final String sort);
}
