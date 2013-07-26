package com.heys.dating.impl.gae.repository;

import static com.heys.dating.util.DatastoreUtil.c;

import java.util.List;

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
		return load().filter("member", c(member)).filter("target", c(target))
				.count();
	}

	@Override
	public void deleteByMember(final String member) {
		final List<Key<BlacklistEntry>> keys = load()
				.filter("member", c(member)).keys().list();
		delete(keys);
	}

	@Override
	public void deleteByMemberAndTarget(final String member, final String target) {
		final List<Key<BlacklistEntry>> keys = load()
				.filter("member", c(member)).filter("target", c(target)).keys()
				.list();
		delete(keys);
	}

}
