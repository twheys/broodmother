package com.heys.dating.message;

import com.heys.dating.Repository;

public interface BlacklistRepository extends Repository<BlacklistEntry> {

	void deleteByMember(final String member);

	void deleteByMemberAndTarget(final String member, final String target);

	int countByMemberAndTarget(final String member, final String target);

}
