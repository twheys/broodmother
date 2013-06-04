package com.heys.dating.service.impl;

import java.util.List;
import java.util.Locale;

import javax.validation.ConstraintViolationException;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.googlecode.objectify.Key;
import com.heys.dating.domain.member.Gender;
import com.heys.dating.domain.member.Member;
import com.heys.dating.domain.member.Profile;
import com.heys.dating.domain.member.ProfileCompletion;
import com.heys.dating.domain.member.RelationshipStatus;
import com.heys.dating.domain.repository.MemberRepository;
import com.heys.dating.domain.repository.ProfileRepository;
import com.heys.dating.security.DatingUserDetails;
import com.heys.dating.service.MemberManager;
import com.heys.dating.service.QueueManager;
import com.heys.dating.service.SearchManager;
import com.heys.dating.util.EnumUtils;

@Service("MemberManager")
public class MemberManangerImpl implements MemberManager {
	private static final Logger logger = LoggerFactory
			.getLogger(MemberManangerImpl.class);

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private SearchManager searchManager;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private QueueManager queueManager;

	@Autowired
	private SaltSource saltSource;

	private String encodePassword(final String password, final Member member) {
		final DatingUserDetails principle = new DatingUserDetails(member);
		final Object salt = saltSource.getSalt(principle);
		final String encodePassword = passwordEncoder.encodePassword(password,
				salt);
		return encodePassword;
	}

	/*
	 * GAE Datastore doesn't support OR clauses in queries, therefore two
	 * queries are performed.
	 */
	@Override
	public Member findByLoginOrEmail(final String identifier) {
		logger.info("Retrieving member details. :: " + identifier);
		Member member;
		if (null != (member = memberRepository.findByLogin(identifier)))
			return member;

		return memberRepository.findByEmail(identifier);
	}

	@Override
	public Profile getProfile(final Key<Profile> profileKey)
			throws NotFoundException {
		logger.info("Retrieving profile for owner. :: " + profileKey.toString());
		final Profile profile = profileRepository.findOne(profileKey);

		logger.info("Profile :: " + profile);

		if (null == profile)
			throw new NotFoundException();

		return profile;
	}

	@Override
	public Profile getProfileForVanity(final String vanity)
			throws NotFoundException {
		logger.info("Retrieving profile. :: " + vanity);
		final Profile profile = profileRepository.findByVanity(vanity);

		if (null == profile)
			throw new NotFoundException();

		return profile;
	}

	private Member initAccount(final String login, final String email,
			final DateTime birthdate, final Locale locale) {
		final Member member = new Member(login, email, birthdate.toDate(),
				locale.toString());
		return member;
	}

	private Profile initProfile(final String login) {
		final Profile profile = new Profile(StringUtils.lowerCase(login));
		profile.setProfileCompletion(ProfileCompletion.MISSING_BASIC_DETAILS);
		return profile;
	}

	@Override
	public ProfileCompletion isProfileComplete(final Key<Profile> profileKey)
			throws NotFoundException {
		logger.info("Evaluating member profile completion. :: " + profileKey);

		return getProfile(profileKey).getProfileCompletion();
	}

	private Member persistMember(final Member member) {
		logger.info("Validating new member. :: " + member.getLogin());
		memberRepository.validate(member);
		logger.info("Persisting new member. :: " + member.getLogin());
		memberRepository.save(member);
		logger.info("Member account created. :: " + member.getLogin());
		return member;
	}

	private Profile persistProfile(final Profile profile) {
		logger.info("Validating profile update. :: " + profile.getVanity());
		profileRepository.validate(profile);
		logger.info("Persisting profile update. :: " + profile.getVanity());
		profileRepository.save(profile);
		logger.info("Profile update success. :: " + profile.getVanity());
		return profile;
	}

	@Override
	public Member register(final String login, final String password,
			final String email, final DateTime birthdate, final Locale locale) {
		logger.info("Registering new member. :: " + login);
		final Member member = initAccount(login, email, birthdate, locale);

		logger.info("Initializing member profile. :: " + login);
		final Profile profile = initProfile(login);
		profileRepository.save(profile);
		logger.info("Member profile created. :: " + profile.getVanity());

		logger.info("Initializing member account. :: " + login);
		final String encodePassword = encodePassword(password, member);
		member.setPassword(encodePassword);
		member.setProfile(Key.create(profile));

		return persistMember(member);
	}

	@Override
	public Member register(final String login, final String password,
			final String email, final DateTime birthdate, final String zipCode,
			final Gender gender, final List<Gender> partnerGender,
			final Integer partnerAgeMin, final Integer partnerAgeMax,
			final Locale locale) {
		logger.info("Registering new member. :: " + login);
		final Member member = initAccount(login, email, birthdate, locale);

		logger.info("Initializing member profile. :: " + login);
		final Profile profile = initProfile(login);
		profile.setGender(gender);
		profile.setPartnerGenders(partnerGender);
		profile.setPartnerAgeMin(partnerAgeMin);
		profile.setPartnerAgeMax(partnerAgeMax);
		persistProfile(profile);
		logger.info("Member profile created. :: " + profile.getVanity());

		logger.info("Initializing member account. :: " + login);
		final String encodePassword = encodePassword(password, member);
		member.setPassword(encodePassword);
		member.setProfile(Key.create(profile));

		return persistMember(member);
	}

	@Override
	public void updateProfileContent(final Profile profile,
			final String description) throws ConstraintViolationException {
		profile.setDescription(description);

		profile.setProfileCompletion(ProfileCompletion.MISSING_PICTURE);
		persistProfile(profile);
	}

	// TODO location data
	@Override
	public void updateProfileDetails(final Profile profile,
			final String status, final String gender, final String zipCode,
			final Integer partnerAgeMin, final Integer partnerAgeMax,
			final List<String> partnerGenders)
			throws ConstraintViolationException {
		final RelationshipStatus vStatus = EnumUtils.toEnum(status,
				RelationshipStatus.INVALID_VALUE);
		final Gender vGender = EnumUtils.toEnum(gender, Gender.INVALID_VALUE);
		final List<Gender> vPartnerGenders = Lists.transform(partnerGenders,
				new Function<String, Gender>() {
					@Override
					public Gender apply(final String from) {
						return EnumUtils.toEnum(gender, Gender.INVALID_VALUE);
					}
				});

		profile.setStatus(vStatus);
		profile.setGender(vGender);
		// profile.setLocation(location);
		profile.setPartnerAgeMin(partnerAgeMin);
		profile.setPartnerAgeMax(partnerAgeMax);
		profile.setPartnerGenders(vPartnerGenders);

		profile.setProfileCompletion(ProfileCompletion.MISSING_PROFILE);
		persistProfile(profile);
	}

	@Override
	public void updateProfilePicture(final Profile profile,
			final Long pictureId, final boolean isProfilePicture,
			final String gallery, final String description,
			final String visibility) throws ConstraintViolationException {
		// TODO Auto-generated method stub

		profile.setProfileCompletion(ProfileCompletion.COMPLETE);
		persistProfile(profile);
	}
}
