package com.heys.dating.manager.impl;

import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.appengine.repackaged.org.joda.time.DateMidnight;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.heys.dating.domain.member.Gender;
import com.heys.dating.domain.member.Member;
import com.heys.dating.domain.member.Profile;
import com.heys.dating.domain.member.ProfileCompletion;
import com.heys.dating.domain.member.RelationshipStatus;
import com.heys.dating.domain.repository.MemberRepository;
import com.heys.dating.domain.repository.ProfileRepository;
import com.heys.dating.manager.MemberManager;
import com.heys.dating.manager.PictureManager;
import com.heys.dating.security.DatingUserDetails;
import com.heys.dating.util.EnumUtils;

@Service("MemberManager")
@Slf4j
public class MemberManagerImpl implements MemberManager {
	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private SaltSource saltSource;

	@Autowired
	private PictureManager pictureManager;

	@Override
	public void addPicture(final Key<Member> memberKey,
			final InputStream stream, final String contentType,
			final String description) throws ConstraintViolationException,
			NotFoundException {
		final Profile profile = getProfileForMember(memberKey);
		pictureManager.addPicture(Key.create(profile), stream, contentType,
				description);
		profile.setProfileCompletion(ProfileCompletion.COMPLETE);
		persistProfile(profile);
	}

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
		log.info("Retrieving member details. :: " + identifier);
		Member member;
		if (null != (member = memberRepository.findByLogin(identifier)))
			return member;

		return memberRepository.findByEmail(identifier);
	}

	@Override
	public ProfileCompletion getProfileComplete(final Key<Member> memberKey)
			throws NotFoundException {
		log.info("Evaluating member profile completion. :: " + memberKey);

		return getProfileForMember(memberKey).getProfileCompletion();
	}

	@Override
	public Profile getProfileForMember(final Key<Member> memberKey)
			throws NotFoundException {
		log.info("Retrieving profile for owner. :: " + memberKey.toString());
		final Profile profile = profileRepository.findByOwner(memberKey);

		log.info("Profile :: " + profile);

		if (null == profile)
			throw new NotFoundException();

		return profile;
	}

	@Override
	public Profile getProfileForVanity(final String vanity)
			throws NotFoundException {
		log.info("Retrieving profile. :: " + vanity);
		final Profile profile = profileRepository.findByVanity(vanity);

		if (null == profile)
			throw new NotFoundException();

		return profile;
	}

	private Member initAccount(final String login, final String email,
			final String password, final DateMidnight birthdate,
			final Locale locale) {
		final Member member = new Member(login, email, birthdate.toDate(),
				locale.toString());
		member.setPassword(password);
		member.setRawPassword(password);
		memberRepository.validate(member);
		return member;
	}

	private Profile initProfile(final String login, final String country) {
		log.info("Initializing member profile. :: " + login);
		final Profile profile = new Profile(StringUtils.lowerCase(login));
		profile.setCountry(country);
		return profile;
	}

	private Member persistMember(final Member member) {
		log.info("Persisting new member. :: " + member.getLogin());
		memberRepository.save(member);
		log.info("Member account created. :: " + member.getLogin());
		return member;
	}

	private Profile persistProfile(final Profile profile) {
		log.info("Persisting profile update. :: " + profile.getVanity());
		profileRepository.save(profile);
		log.info("Profile update success. :: " + profile.getVanity());
		return profile;
	}

	@Override
	public Member register(final String login, final String password,
			final String email, final DateMidnight birthdate,
			final Locale locale) {
		log.info("Registering new member. :: " + login);
		final Member member = initAccount(login, email, password, birthdate,
				locale);

		log.info("Initializing member account. :: " + login);
		final String encodePassword = encodePassword(password, member);
		member.setPassword(encodePassword);
		final Member persistedMember = persistMember(member);
		log.info("Member account created. :: " + login);

		final Profile profile = initProfile(login, locale.getCountry());
		profile.setOwner(Key.create(persistedMember));
		profile.setProfileCompletion(ProfileCompletion.STEP1_MISSING_BASIC_DETAILS);
		persistProfile(profile);

		persistedMember.setProfile(Ref.create(profile));
		return memberRepository.save(persistedMember);
	}

	@Override
	public Member register(final String login, final String password,
			final String email, final DateMidnight birthdate,
			final String zipCode, final Locale locale, final Gender gender,
			final List<Gender> partnerGender, final Integer partnerAgeMin,
			final Integer partnerAgeMax) throws ValidationException {
		log.info("Registering new member. :: " + login);
		final Member member = initAccount(login, email, password, birthdate,
				locale);
		log.info("Initializing member account. :: " + login);
		final String encodePassword = encodePassword(password, member);
		member.setPassword(encodePassword);
		final Member persistedMember = persistMember(member);
		log.info("Member account created. :: " + login);

		log.info("Initializing member profile. :: " + login);
		final Profile profile = initProfile(login, locale.getCountry());
		profile.setOwner(Key.create(persistedMember));
		profile.setZipCode(zipCode);
		profile.setGender(gender);
		profile.setPartnerGenders(partnerGender);
		profile.setPartnerAgeMin(partnerAgeMin);
		profile.setPartnerAgeMax(partnerAgeMax);
		profile.setProfileCompletion(ProfileCompletion.STEP2_MISSING_PROFILE);
		persistProfile(profile);
		log.info("Member profile created. :: " + profile.getVanity());

		persistedMember.setProfile(Ref.create(profile));
		return memberRepository.save(persistedMember);
	}

	@Override
	public void updateProfileContent(final Key<Member> memberKey,
			final String description) throws ConstraintViolationException,
			NotFoundException {
		final Profile profile = getProfileForMember(memberKey);
		profile.setDescription(description);

		profile.setProfileCompletion(ProfileCompletion.STEP3_MISSING_PICTURE);
		persistProfile(profile);
	}

	// TODO location data
	@Override
	public void updateProfileDetails(final Key<Member> memberKey,
			final String status, final String gender, final String zipCode,
			final Integer partnerAgeMin, final Integer partnerAgeMax,
			final List<String> partnerGenders)
			throws ConstraintViolationException, NotFoundException {
		final Profile profile = getProfileForMember(memberKey);
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

		profile.setProfileCompletion(ProfileCompletion.STEP2_MISSING_PROFILE);
		persistProfile(profile);
	}
}
