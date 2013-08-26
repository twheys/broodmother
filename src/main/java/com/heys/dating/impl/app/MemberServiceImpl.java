package com.heys.dating.impl.app;

import java.util.List;
import java.util.Locale;

import javax.validation.ValidationException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.appengine.repackaged.org.joda.time.DateMidnight;
import com.googlecode.objectify.Ref;
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
	private PictureService pictureService;

	@Autowired
	private ProfileService profileService;

	private Member createMember(final String login, final String email,
			final String encodedPassword, final DateMidnight birthdate,
			final Locale locale) {
		log.debug("Initializing member account. :: " + login);
		final Member member = new Member(login, email, birthdate.toDate(),
				locale.toString());
		member.setPassword(encodedPassword);
		memberRepository.validate(member);

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
