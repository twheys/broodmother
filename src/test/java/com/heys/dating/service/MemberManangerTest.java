package com.heys.dating.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.InputStream;
import java.util.Locale;
import java.util.Map;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.google.appengine.labs.repackaged.com.google.common.collect.Lists;
import com.google.appengine.repackaged.org.joda.time.DateMidnight;
import com.googlecode.objectify.Key;
import com.heys.dating.domain.member.Gender;
import com.heys.dating.domain.member.Member;
import com.heys.dating.domain.member.MemberRole;
import com.heys.dating.domain.member.Profile;
import com.heys.dating.domain.member.ProfileCompletion;
import com.heys.dating.domain.member.RelationshipStatus;
import com.heys.dating.manager.impl.NotFoundException;

public class MemberManangerTest extends MemberTest {

	@Test
	public void findMemberByEmail() {
		final Map<String, Object> registration = getLongRegValues();
		longReg(registration);

		final Member member = memberMananger
				.findByLoginOrEmail((String) registration.get(EMAIL));
		assertEquals(member.getLogin(), registration.get(LOGIN));
		assertEquals(member.getEmail(), registration.get(EMAIL));
	}

	@Test
	public void findMemberByLogin() {
		final Map<String, Object> registration = getLongRegValues();
		longReg(registration);

		final Member member = memberMananger
				.findByLoginOrEmail((String) registration.get(LOGIN));
		assertEquals(member.getLogin(), registration.get(LOGIN));
		assertEquals(member.getEmail(), registration.get(EMAIL));
	}

	@Test
	public void profileCompletionProgression() throws NotFoundException {
		final Map<String, Object> registration = getShortRegValues();
		final Member member = shortReg(registration);

		final Key<Member> memberKey = Key.create(member);
		assertEquals(ProfileCompletion.STEP1_MISSING_BASIC_DETAILS,
				memberMananger.getProfileComplete(memberKey));

		memberMananger.updateProfileDetails(memberKey,
				RelationshipStatus.SINGLE.toString(), Gender.FEMALE.toString(),
				"12345", 18, 35, Lists.newArrayList(Gender.MALE.toString()));

		assertEquals(ProfileCompletion.STEP2_MISSING_PROFILE,
				memberMananger.getProfileComplete(memberKey));

		memberMananger.updateProfileContent(memberKey, "I am a test user.");

		assertEquals(ProfileCompletion.STEP3_MISSING_PICTURE,
				memberMananger.getProfileComplete(memberKey));

		final InputStream stream = this.getClass().getResourceAsStream(
				"test-image.jpeg");

		memberMananger.addPicture(memberKey, stream, "image/jpeg",
				"This is my profile picture");

		assertEquals(ProfileCompletion.COMPLETE,
				memberMananger.getProfileComplete(memberKey));

	}

	@Test
	public void profileCreationLongReg() throws NotFoundException {
		final Map<String, Object> registration = getLongRegValues();
		final Member member = longReg(registration);

		final Profile profile = memberMananger.getProfileForMember(Key
				.create(member));
		assertEquals(StringUtils.lowerCase((String) registration.get(LOGIN)),
				profile.getVanity());
		assertEquals(ProfileCompletion.STEP2_MISSING_PROFILE,
				profile.getProfileCompletion());
		assertEquals(registration.get(ZIPCODE), profile.getZipCode());
		assertEquals(((Locale) registration.get(LOCALE)).getCountry(),
				profile.getCountry());
		assertEquals(registration.get(GENDER), profile.getGender());
		assertEquals(registration.get(PGENDER), profile.getPartnerGenders());
		assertEquals(registration.get(PAGEMIN), profile.getPartnerAgeMin());
		assertEquals(registration.get(PAGEMAX), profile.getPartnerAgeMax());
		assertNull(profile.getProfilePicture());
		assertNotNull(profile.getGalleries());
	}

	@Test
	public void profileCreationShortReg() throws NotFoundException {
		final Map<String, Object> registration = getLongRegValues();
		final Member member = shortReg(registration);

		final Profile profile = memberMananger.getProfileForMember(Key
				.create(member));
		assertEquals(StringUtils.lowerCase((String) registration.get(LOGIN)),
				profile.getVanity());
		assertEquals(((Locale) registration.get(LOCALE)).getCountry(),
				profile.getCountry());
		assertNull(profile.getGender());
		assertNotNull(profile.getPartnerGenders());
		assertTrue(profile.getPartnerGenders().isEmpty());
		assertNull(profile.getPartnerAgeMin());
		assertNull(profile.getPartnerAgeMax());
		assertNull(profile.getProfilePicture());
		assertNotNull(profile.getGalleries());
	}

	@Test
	public void profileVanityCreated() throws NotFoundException {
		final Map<String, Object> registration = getLongRegValues();
		final Member member = longReg(registration);

		final Profile profile = memberMananger.getProfileForVanity(member
				.getLogin());
		assertEquals(profile.getZipCode(), registration.get(ZIPCODE));
		assertEquals(profile.getCountry(),
				((Locale) registration.get(LOCALE)).getCountry());
		assertEquals(profile.getGender(), registration.get(GENDER));
		assertEquals(profile.getPartnerGenders(), registration.get(PGENDER));
		assertEquals(profile.getPartnerAgeMin(), registration.get(PAGEMIN));
		assertEquals(profile.getPartnerAgeMax(), registration.get(PAGEMAX));
		assertNull(profile.getProfilePicture());
		assertNotNull(profile.getGalleries());
	}

	@Test
	public void registerLong() {
		final Map<String, Object> registration = getLongRegValues();
		final Member member = longReg(registration);

		assertEquals(member.getLogin(), registration.get(LOGIN));
		assertNull(member.getRawPassword());
		assertNotNull(member.getPassword());
		assertEquals(member.getEmail(), registration.get(EMAIL));
		assertEquals(member.getBirthdate(),
				((DateMidnight) registration.get(BIRTHDATE)).toDate());
		assertEquals(member.getLocale(), registration.get(LOCALE).toString());
		assertTrue(member.getPrivileges().contains(MemberRole.ROLE_USER));
		assertFalse(member.isActivated());
		assertFalse(member.isBanned());
		assertFalse(member.isHasExpiredCredentials());
		assertFalse(member.isLocked());
		assertTrue(member.isEnabled());
		assertNotNull(member.getProfile());
	}

	@Test
	public void registerShort() {
		final Map<String, Object> registration = getShortRegValues();
		final Member member = shortReg(registration);

		assertEquals(member.getLogin(), registration.get(LOGIN));
		assertNull(member.getRawPassword());
		assertNotNull(member.getPassword());
		assertEquals(member.getEmail(), registration.get(EMAIL));
		assertEquals(member.getBirthdate(),
				((DateMidnight) registration.get(BIRTHDATE)).toDate());
		assertEquals(member.getLocale(), registration.get(LOCALE).toString());
		assertTrue(member.getPrivileges().contains(MemberRole.ROLE_USER));
		assertFalse(member.isActivated());
		assertFalse(member.isBanned());
		assertFalse(member.isHasExpiredCredentials());
		assertFalse(member.isLocked());
		assertTrue(member.isEnabled());
		assertNotNull(member.getProfile());
	}

	private void registerValidation(final String field, final Object value) {
		final Map<String, Object> registration = getLongRegValues();
		registration.put(field, value);
		try {
			longReg(registration);
		} catch (final ConstraintViolationException e) {
			fail("Received ValidationException for " + field);
		}
	}

	private void registerValidation(final String field, final Object value,
			final String message) {
		registerValidation(field, field, value, message);
	}

	private void registerValidation(final String field, final String realField,
			final Object value, final String message) {
		final Map<String, Object> registration = getLongRegValues();
		registration.put(field, value);
		try {
			longReg(registration);
		} catch (final ConstraintViolationException e) {
			validateConstraint(e, realField, message);
			return;
		}
		fail("Expected ValidationException for " + realField);
	}

	@Test
	public void validateBirthdate() {
		registerValidation(BIRTHDATE, new DateMidnight().minusYears(18));

		registerValidation(BIRTHDATE, new DateMidnight().minusYears(18)
				.plusDays(1), "minage");
		registerValidation(BIRTHDATE, new DateMidnight().minusYears(17),
				"minage");

	}

	private void validateConstraint(final ConstraintViolationException cve,
			final String propertyPath, final String message) {
		assertEquals(1, cve.getConstraintViolations().size());
		final ConstraintViolation<?> c = cve.getConstraintViolations()
				.iterator().next();
		assertEquals(propertyPath, c.getPropertyPath().toString());
		assertEquals(message, c.getMessage());
	}

	@Test
	public void validateEmail() {
		registerValidation(EMAIL, "test@test.com");
		registerValidation(EMAIL, "test01@test.com");

		registerValidation(EMAIL, null, "notnull");

		registerValidation(EMAIL, "srgargarga", "email");
	}

	@Test
	public void validateLogin() {
		registerValidation(LOGIN, "ABC-abc123");
		registerValidation(LOGIN, "ABC_abc123");
		registerValidation(LOGIN, "A2-3BC5_abc123");
		registerValidation(LOGIN, "A23_BC5_ab-c123");

		registerValidation(LOGIN, null, "notnull");
		registerValidation(LOGIN, "abcd", "length 5 32");
		registerValidation(LOGIN, "abcdefghijklmnopqrstuvwxyz1234567890",
				"length 5 32");
		registerValidation(LOGIN, "ABCdef123!§!$", "pattern");
		registerValidation(LOGIN, "ABC--def123", "pattern");
		registerValidation(LOGIN, "ABC__def123", "pattern");
		registerValidation(LOGIN, "1ABCdef123", "pattern");
		registerValidation(LOGIN, "_ABCdef123", "pattern");
		registerValidation(LOGIN, "-ABCdef123", "pattern");
	}

	@Test
	public void validatePassword() {
		registerValidation(PASSWORD, "Abc12%");
		registerValidation(PASSWORD, "_Ab12%");
		registerValidation(PASSWORD, "Abc-123%");

		registerValidation(PASSWORD, null, "notnull");

		registerValidation(PASSWORD, "rawPassword", "Abc45", "length 6 132");
		registerValidation(PASSWORD, "rawPassword", "ABce$%", "pattern");
		registerValidation(PASSWORD, "rawPassword", "ce$%12", "pattern");
		registerValidation(PASSWORD, "rawPassword", "$%12AB", "pattern");
	}
}
