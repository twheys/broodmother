package com.heys.dating.member;

import java.util.List;
import java.util.Locale;

import javax.validation.ConstraintViolationException;

import com.google.appengine.repackaged.org.joda.time.DateMidnight;

public interface MemberService {

	Member findByLoginOrEmail(final String identifier);

	Member register(final String login, final String password,
			final String email, final DateMidnight birthdate,
			final Locale locale) throws ConstraintViolationException;

	Member register(final String login, final String password,
			final String email, final DateMidnight birthdate,
			final String zipCode, final Locale locale, final Gender gender,
			final List<Gender> partnerGender, final Integer partnerAgeMin,
			final Integer partnerAgeMax) throws ConstraintViolationException;
}
