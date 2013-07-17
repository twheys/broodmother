package com.heys.dating.manager;

import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import javax.validation.ConstraintViolationException;

import com.google.appengine.repackaged.org.joda.time.DateMidnight;
import com.googlecode.objectify.Key;
import com.heys.dating.domain.member.Gender;
import com.heys.dating.domain.member.Member;
import com.heys.dating.domain.member.Profile;
import com.heys.dating.domain.member.ProfileCompletion;
import com.heys.dating.manager.impl.NotFoundException;

public interface MemberManager {

	Member findByLoginOrEmail(final String identifier);

	Profile getProfileForMember(final Key<Member> memberKey)
			throws NotFoundException;

	Profile getProfileForVanity(final String vanity) throws NotFoundException;

	ProfileCompletion getProfileComplete(final Key<Member> memberKey)
			throws NotFoundException;

	Member register(final String login, final String password,
			final String email, final DateMidnight birthdate,
			final Locale locale) throws ConstraintViolationException;

	Member register(final String login, final String password,
			final String email, final DateMidnight birthdate,
			final String zipCode, final Locale locale, final Gender gender,
			final List<Gender> partnerGender, final Integer partnerAgeMin,
			final Integer partnerAgeMax) throws ConstraintViolationException;

	void updateProfileContent(final Key<Member> memberKey,
			final String description) throws ConstraintViolationException,
			NotFoundException;

	void updateProfileDetails(final Key<Member> memberKey, final String status,
			final String gender, final String zipCode,
			final Integer partnerAgeMin, final Integer partnerAgeMax,
			final List<String> partnerGenders)
			throws ConstraintViolationException, NotFoundException;

	void addPicture(final Key<Member> memberKey, final InputStream stream,
			final String contentType, final String description)
			throws ConstraintViolationException, NotFoundException;
}
