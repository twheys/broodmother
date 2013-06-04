package com.heys.dating.service;

import java.util.List;
import java.util.Locale;

import javax.validation.ConstraintViolationException;

import org.joda.time.DateTime;

import com.googlecode.objectify.Key;
import com.heys.dating.domain.member.Gender;
import com.heys.dating.domain.member.Member;
import com.heys.dating.domain.member.Profile;
import com.heys.dating.domain.member.ProfileCompletion;
import com.heys.dating.service.impl.NotFoundException;

public interface MemberManager {

	Member findByLoginOrEmail(String identifier);

	Profile getProfile(Key<Profile> profileKey) throws NotFoundException;

	Profile getProfileForVanity(String vanity) throws NotFoundException;

	ProfileCompletion isProfileComplete(final Key<Profile> key)
			throws NotFoundException;

	Member register(String login, String password, String email,
			DateTime birthdate, Locale locale)
			throws ConstraintViolationException;

	Member register(String login, String password, String email,
			DateTime birthdate, String zipCode, Gender gender,
			List<Gender> partnerGender, Integer partnerAgeMin,
			Integer partnerAgeMax, Locale locale)
			throws ConstraintViolationException;

	void updateProfileContent(Profile profile, String description)
			throws ConstraintViolationException;

	void updateProfileDetails(Profile profile, String status, String gender,
			String zipCode, Integer partnerAgeMin, Integer partnerAgeMax,
			List<String> partnerGenders) throws ConstraintViolationException;

	void updateProfilePicture(Profile profile, Long pictureId,
			boolean isProfilePicture, String gallery, String description,
			String visibility) throws ConstraintViolationException;

}
