package com.heys.dating.domain.repository;

import com.heys.dating.domain.Repository;
import com.heys.dating.domain.member.BlacklistEntry;

public interface BlacklistRepository extends Repository<BlacklistEntry> {

	void deleteByMember(String member);

	void deleteByMemberAndTargetMember(String member, String targetMember);

	BlacklistEntry findByMemberAndTargetMember(String member,
			String targetMember);

}
