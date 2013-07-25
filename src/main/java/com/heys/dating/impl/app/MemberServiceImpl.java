package com.heys.dating.impl.app;

import java.util.List;
import java.util.Locale;

import javax.validation.ValidationException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.appengine.repackaged.org.joda.time.DateMidnight;
import com.googlecode.objectify.Ref;
import com.heys.dating.impl.gae.security.DatingUserDetails;
import com.heys.dating.member.Gender;
import com.heys.dating.member.Member;
import com.heys.dating.member.MemberRepository;
import com.heys.dating.member.MemberService;
import com.heys.dating.picture.PictureService;
import com.heys.dating.profile.Profile;
import com.heys.dating.profile.ProfileService;

@Service("MemberService")
@Slf4j
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private ProfileService profileService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private SaltSource saltSource;

	@Autowired
	private PictureService pictureService;

	private String createAuthentication(final Member member,
			final String password) {
		final DatingUserDetails principle = new DatingUserDetails(member);
		final Object salt = saltSource.getSalt(principle);
		final String encodePassword = passwordEncoder.encodePassword(password,
				salt);
		return encodePassword;
	}

	private Member createMember(final String login, final String email,
			final String password, final DateMidnight birthdate,
			final Locale locale) {
		log.debug("Initializing member account. :: " + login);
		final Member member = initAccount(login, email, password, birthdate,
				locale);
		final String authentication = createAuthentication(member, password);
		member.setPassword(authentication);

		memberRepository.validate(member);
		log.info("Member account created. :: " + login);
		return memberRepository.save(member);
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

	@Override
	public Member register(final String login, final String password,
			final String email, final DateMidnight birthdate,
			final Locale locale) {
		log.debug("Registering new member. :: " + login);
		final Member member = createMember(login, email, password, birthdate,
				locale);

		final Profile profile = profileService.buildProfile(member, locale);
		member.setProfile(Ref.create(profile));
		return memberRepository.save(member);
	}

	@Override
	public Member register(final String login, final String password,
			final String email, final DateMidnight birthdate,
			final String zipCode, final Locale locale, final Gender gender,
			final List<Gender> partnerGender, final Integer partnerAgeMin,
			final Integer partnerAgeMax) throws ValidationException {
		log.debug("Registering new member. :: " + login);
		final Member member = createMember(login, email, password, birthdate,
				locale);

		final Profile profile = profileService.buildProfile(member, locale,
				zipCode, gender, partnerGender, partnerAgeMin, partnerAgeMax);
		member.setProfile(Ref.create(profile));
		return memberRepository.save(member);
	}
}
