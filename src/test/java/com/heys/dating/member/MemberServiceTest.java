package com.heys.dating.member;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Locale;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.appengine.repackaged.org.joda.time.DateMidnight;
import com.heys.dating.profile.Profile;
import com.heys.dating.test.MemberTest;
import com.heys.dating.util.DatastoreUtil;

public class MemberServiceTest extends MemberTest {

	@Autowired
	private MemberService memberSvc;

	@Test
	public void getByLogin() {
		final Map<String, Object> registration = getShortRegValues();
		final Member member = shortReg(registration);

		assertEquals(member, memberSvc.findByLoginOrEmail(member.getLogin()));
		assertEquals(member, memberSvc.findByLoginOrEmail(member.getEmail()));
	}

	@Test
	public void registerLong() {
		final Map<String, Object> registration = getLongRegValues();
		final Member member = longReg(registration);

		assertEquals(registration.get(LOGIN), member.getLogin());
		assertNull(member.getRawPassword());
		assertNotNull(member.getPassword());
		assertEquals(registration.get(EMAIL), member.getEmail());
		assertEquals(((DateMidnight) registration.get(BIRTHDATE)).toDate(),
				member.getBirthdate());
		assertEquals(registration.get(LOCALE).toString(), member.getLocale());
		assertTrue(member.getPrivileges().contains(MemberRole.ROLE_USER));
		assertFalse(member.isActivated());
		assertFalse(member.isBanned());
		assertFalse(member.isHasExpiredCredentials());
		assertFalse(member.isLocked());
		assertTrue(member.isEnabled());
		assertNotNull(member.getProfile());

		final Profile profile = member.getProfile().get();
		assertNotNull(profile);
		assertNotNull(profile.getPartnerGenders());
		assertNotNull(profile.getGalleries());
		assertNotNull(profile.getOwner());
		assertEquals(DatastoreUtil.c(member.getLogin()), profile.getVanity());

		assertEquals(registration.get(GENDER), profile.getGender());
		assertEquals(((Locale) registration.get(LOCALE)).getCountry(),
				profile.getCountry());
		assertEquals(registration.get(PAGEMIN), profile.getPartnerAgeMin());
		assertEquals(registration.get(PAGEMAX), profile.getPartnerAgeMax());
		assertEquals(1, profile.getPartnerGenders().size());
		assertEquals(registration.get(PGENDER), profile.getPartnerGenders());
	}

	@Test
	public void registerShort() {
		final Map<String, Object> registration = getShortRegValues();
		final Member member = shortReg(registration);

		assertEquals(registration.get(LOGIN), member.getLogin());
		assertNull(member.getRawPassword());
		assertNotNull(member.getPassword());
		assertEquals(registration.get(EMAIL), member.getEmail());
		assertEquals(((DateMidnight) registration.get(BIRTHDATE)).toDate(),
				member.getBirthdate());
		assertEquals(registration.get(LOCALE).toString(), member.getLocale());
		assertTrue(member.getPrivileges().contains(MemberRole.ROLE_USER));
		assertFalse(member.isActivated());
		assertFalse(member.isBanned());
		assertFalse(member.isHasExpiredCredentials());
		assertFalse(member.isLocked());
		assertTrue(member.isEnabled());
		assertNotNull(member.getProfile());

		final Profile profile = member.getProfile().get();
		assertNotNull(profile);
		assertNotNull(profile.getPartnerGenders());
		assertNotNull(profile.getGalleries());
		assertNotNull(profile.getOwner());
		assertEquals(DatastoreUtil.c(member.getLogin()), profile.getVanity());
		assertEquals(Gender.UNKNOWN, profile.getGender());
		assertEquals(((Locale) registration.get(LOCALE)).getCountry(),
				profile.getCountry());
		assertNull(profile.getPartnerAgeMin());
		assertNull(profile.getPartnerAgeMax());
		assertEquals(0, profile.getPartnerGenders().size());
	}
}
