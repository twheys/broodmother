package com.heys.dating.domain.repository.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.heys.dating.domain.AbstractDatastoreRepository;
import com.heys.dating.domain.member.BlacklistEntry;
import com.heys.dating.domain.repository.BlacklistRepository;

@Repository
public final class BlacklistRepositoryDatastoreImpl extends
		AbstractDatastoreRepository<BlacklistEntry> implements
		BlacklistRepository {

	public BlacklistRepositoryDatastoreImpl() {
		super(BlacklistEntry.class);
	}

	@Override
	public int countByMemberAndTarget(final String member,
			final String target) {
		final String _member = StringUtils.upperCase(member);
		final String _target = StringUtils.upperCase(target);

		return load().filter("member", _member).filter("target", _target)
				.count();
	}

	@Override
	public void deleteByMember(final String member) {
		final String _member = StringUtils.upperCase(member);

		final List<BlacklistEntry> entries = load().filter("member", _member)
				.list();
		delete(entries);
	}

	@Override
	public void deleteByMemberAndTarget(final String member,
			final String target) {
		final String _member = StringUtils.upperCase(member);
		final String _target = StringUtils.upperCase(target);

		final List<BlacklistEntry> entries = load().filter("member", _member)
				.filter("target", _target).list();
		delete(entries);
	}

}
