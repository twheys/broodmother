package com.heys.dating.domain.repository;

import com.heys.dating.domain.Repository;
import com.heys.dating.domain.member.BlacklistEntry;

public interface BlacklistRepository extends Repository<BlacklistEntry> {

	void deleteByMember(final String member);

	void deleteByMemberAndTarget(final String member, final String target);

	int countByMemberAndTarget(final String member, final String target);

}
