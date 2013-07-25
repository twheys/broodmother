package com.heys.dating.impl.app;

import java.util.List;
import java.util.Locale;

import javax.validation.ConstraintViolationException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.googlecode.objectify.Key;
import com.heys.dating.member.Gender;
import com.heys.dating.member.Member;
import com.heys.dating.profile.Profile;
import com.heys.dating.profile.ProfileRepository;
import com.heys.dating.profile.ProfileService;
import com.heys.dating.profile.RelationshipStatus;
import com.heys.dating.util.EnumUtils;

@Service("ProfileService")
@Slf4j
public class ProfileServiceImpl implements ProfileService {

	@Autowired
	private ProfileRepository profileRepository;

	@Override
	public Profile buildProfile(final Member member, final Locale locale) {
		return createProfile(member, member.getLogin(), Gender.UNKNOWN, locale);
	}

	@Override
	public Profile buildProfile(final Member member, final Locale locale,
			final String zipCode, final Gender gender,
			final List<Gender> partnerGenders, final Integer partnerAgeMin,
			final Integer partnerAgeMax) {
		final Profile profile = createProfile(member, member.getLogin(),
				gender, locale);
		profile.setZipCode(zipCode);
		profile.setPartnerGenders(partnerGenders);
		profile.setPartnerAgeMin(partnerAgeMin);
		profile.setPartnerAgeMax(partnerAgeMax);
		return profileRepository.save(profile);
	}

	private Profile createProfile(final Member member, final String vanity,
			final Gender gender, final Locale locale) {
		if (null != member.getProfile())
			throw new IllegalArgumentException("Member already has a profile!");

		log.debug("Creating profile for member. :: " + member.getLogin());
		final Profile profile = new Profile(Key.create(member), vanity, gender,
				locale.getCountry());

		profileRepository.validate(profile);
		log.info("Profile created. :: " + profile);
		return profileRepository.save(profile);
	}

	@Override
	public Profile getProfileForMember(final Member member) {
		log.info("Retrieving profile for owner. :: " + member.getLogin());
		final Profile profile = profileRepository.findByOwner(member);

		log.info("Profile :: " + profile);

		if (null == profile)
			throw new RuntimeException("Profile not initialized for member "
					+ member.getLogin() + "?");

		return profile;
	}

	@Override
	public Profile getProfileForVanity(final String vanity)
			throws NotFoundException {
		log.info("Retrieving profile. :: " + vanity);
		final Profile profile = profileRepository.findByVanity(vanity);

		if (null == profile)
			throw new NotFoundException(Profile.class, "Not found for vanity: "
					+ vanity);

		return profile;
	}

	@Override
	public Profile updateProfileContent(final Member member,
			final String description) throws ConstraintViolationException {
		final Profile profile = getProfileForMember(member);
		profile.setDescription(description);

		return profileRepository.save(profile);
	}

	// TODO location data
	@Override
	public Profile updateProfileDetails(final Member member,
			final String status, final String gender, final String zipCode,
			final Integer partnerAgeMin, final Integer partnerAgeMax,
			final List<String> partnerGenders)
			throws ConstraintViolationException {
		final Profile profile = getProfileForMember(member);
		final RelationshipStatus vStatus = EnumUtils.toEnum(status,
				RelationshipStatus.INVALID_VALUE);
		final Gender vGender = EnumUtils.toEnum(gender, Gender.INVALID_VALUE);
		final List<Gender> vPartnerGenders = Lists.transform(partnerGenders,
				new Function<String, Gender>() {
					@Override
					public Gender apply(final String from) {
						return EnumUtils.toEnum(from, Gender.INVALID_VALUE);
					}
				});

		profile.setStatus(vStatus);
		profile.setGender(vGender);
		profile.setPartnerAgeMin(partnerAgeMin);
		profile.setPartnerAgeMax(partnerAgeMax);
		profile.setPartnerGenders(vPartnerGenders);

		return profileRepository.save(profile);
	}
}
