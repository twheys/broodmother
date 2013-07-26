package com.heys.dating.profile;

import com.heys.dating.Repository;
import com.heys.dating.member.Member;

public interface ProfileRepository extends Repository<Profile> {

	Profile findByOwner(final Member member);

	Profile findByVanity(final String vanity);

}
