package com.heys.dating.domain.repository;

import com.googlecode.objectify.Key;
import com.heys.dating.domain.Repository;
import com.heys.dating.domain.member.Member;
import com.heys.dating.domain.member.Profile;

public interface ProfileRepository extends Repository<Profile> {

	Profile findByOwner(Key<Member> memberKey);

	Profile findByVanity(String vanity);

}
