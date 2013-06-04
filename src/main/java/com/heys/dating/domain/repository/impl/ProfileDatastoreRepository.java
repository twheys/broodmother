package com.heys.dating.domain.repository.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.googlecode.objectify.Key;
import com.heys.dating.domain.AbstractRepository;
import com.heys.dating.domain.member.Member;
import com.heys.dating.domain.member.Profile;
import com.heys.dating.domain.repository.ProfileRepository;

@Repository
public class ProfileDatastoreRepository extends AbstractRepository<Profile>
		implements ProfileRepository {

	public ProfileDatastoreRepository() {
		super(Profile.class);
	}

	@Override
	public Profile findByOwner(final Key<Member> memberKey) {
		return load().ancestor(memberKey).first().now();
	}

	@Override
	public Profile findByVanity(final String vanity) {
		return load().filter("vanity", StringUtils.lowerCase(vanity)).first()
				.now();
	}
}
