package com.heys.dating.profile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Locale;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.heys.dating.impl.app.NotFoundException;
import com.heys.dating.member.Member;
import com.heys.dating.test.MemberTest;

public class ProfileServiceTest extends MemberTest {

	@Autowired
	private ProfileService profileSvc;

	@Test(expected = Exception.class)
	public void create2ndProfileForMember() {
		final Member member = longReg(getLongRegValues());
		profileSvc.buildProfile(member, Locale.US);
		fail();
	}

	// @Test
	// public void getProfileForMember() throws NotFoundException {
	// final Member member = longReg(getLongRegValues());
	// final Profile profile = profileSvc.getProfileForMember(member);
	//
	// assertEquals(member.getProfile().get(), profile);
	// }

	@Test
	public void getProfileForVanity() throws NotFoundException {
		final Member member = longReg(getLongRegValues());
		final Profile profile1 = profileSvc.getProfileForVanity(member
				.getLogin());

		assertEquals(member.getProfile().get(), profile1);

		final Profile profile2 = profileSvc.getProfileForVanity(member
				.getLogin().toUpperCase());

		assertEquals(member.getProfile().get(), profile2);
	}

	@Test(expected = NotFoundException.class)
	public void getProfileForVanity_NonExistant() throws NotFoundException {
		profileSvc.getProfileForVanity("2aklfgwjh4nroq3hrfowernfvbj");
		fail();
	}
}
