package com.heys.dating.profile;

import java.util.List;
import java.util.Locale;

import javax.validation.ConstraintViolationException;

import com.heys.dating.impl.app.NotFoundException;
import com.heys.dating.member.Gender;
import com.heys.dating.member.Member;

public interface ProfileService {

	Profile buildProfile(final Member member, final Locale locale);

	Profile buildProfile(final Member member, final Locale locale,
			final String zipCode, final Gender gender,
			final List<Gender> partnerGender, final Integer partnerAgeMin,
			final Integer partnerAgeMax);

	Profile getProfileForVanity(final String vanity) throws NotFoundException;

	Profile updateProfileContent(final Member member, final String description)
			throws ConstraintViolationException;

	Profile updateProfileDetails(final Member member, final String status,
			final String gender, final String zipCode,
			final Integer partnerAgeMin, final Integer partnerAgeMax,
			final List<String> partnerGenders)
			throws ConstraintViolationException;
}
