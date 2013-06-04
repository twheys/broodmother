package com.heys.dating.domain.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.heys.dating.domain.AbstractRepository;
import com.heys.dating.domain.member.BlacklistEntry;
import com.heys.dating.domain.repository.BlacklistRepository;

@Repository
public class BlacklistDatastoreRepository extends
		AbstractRepository<BlacklistEntry> implements BlacklistRepository {

	public BlacklistDatastoreRepository() {
		super(BlacklistEntry.class);
	}

	@Override
	public void deleteByMember(final String member) {
		final List<BlacklistEntry> entries = load().filter("member", member)
				.list();
		delete(entries);
	}

	@Override
	public void deleteByMemberAndTargetMember(final String member,
			final String targetMember) {
		final List<BlacklistEntry> entries = load().filter("member", member)
				.filter("targetMember", targetMember).list();
		delete(entries);
	}

	@Override
	public BlacklistEntry findByMemberAndTargetMember(final String member,
			final String targetMember) {
		return load().filter("member", member)
				.filter("targetMember", targetMember).first().now();
	}

}
