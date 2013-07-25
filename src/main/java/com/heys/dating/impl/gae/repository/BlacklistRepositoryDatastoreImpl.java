package com.heys.dating.impl.gae.repository;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.googlecode.objectify.Key;
import com.heys.dating.message.BlacklistEntry;
import com.heys.dating.message.BlacklistRepository;

@Repository
public final class BlacklistRepositoryDatastoreImpl extends
		AbstractDatastoreRepository<BlacklistEntry> implements
		BlacklistRepository {

	public BlacklistRepositoryDatastoreImpl() {
		super(BlacklistEntry.class);
	}

	@Override
	public int countByMemberAndTarget(final String member, final String target) {
		final String _member = StringUtils.upperCase(member);
		final String _target = StringUtils.upperCase(target);

		return load().filter("member", _member).filter("target", _target)
				.count();
	}

	@Override
	public void deleteByMember(final String member) {
		final String _member = StringUtils.upperCase(member);

		final List<Key<BlacklistEntry>> keys = load().filter("member", _member)
				.keys().list();
		delete(keys);
	}

	@Override
	public void deleteByMemberAndTarget(final String member, final String target) {
		final String _member = StringUtils.upperCase(member);
		final String _target = StringUtils.upperCase(target);

		final List<Key<BlacklistEntry>> keys = load().filter("member", _member)
				.filter("target", _target).keys().list();
		delete(keys);
	}

}
