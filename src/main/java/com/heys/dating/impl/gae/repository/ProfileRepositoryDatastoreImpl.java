package com.heys.dating.impl.gae.repository;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.heys.dating.member.Member;
import com.heys.dating.profile.Profile;
import com.heys.dating.profile.ProfileRepository;

@Repository
public class ProfileRepositoryDatastoreImpl extends
		AbstractDatastoreRepository<Profile> implements ProfileRepository {

	public ProfileRepositoryDatastoreImpl() {
		super(Profile.class);
	}

	@Override
	public Profile findByOwner(final Member member) {
		return load().ancestor(member).first().now();
	}

	@Override
	public Profile findByVanity(final String vanity) {
		return load().filter("vanity", StringUtils.lowerCase(vanity)).first()
				.now();
	}
}
