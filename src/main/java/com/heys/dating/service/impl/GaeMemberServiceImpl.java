package com.heys.dating.service.impl;

import java.util.logging.Logger;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.heys.dating.domain.repository.MemberRepository;
import com.heys.dating.domain.user.Member;
import com.heys.dating.security.DatingUserDetails;
import com.heys.dating.service.MemberService;

@Service("memberService")
public class GaeMemberServiceImpl implements MemberService {
	private static final Logger logger = Logger
			.getLogger(GaeMemberServiceImpl.class.getName());

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private SaltSource saltSource;

	private Member createNewCustomer(final String login, final String password,
			final String email, final DateTime birthdate) {
		logger.fine("Registering new customer: " + login);
		final Member member = new Member(login, email, birthdate.toDate());

		final DatingUserDetails principle = new DatingUserDetails(member);
		final Object salt = saltSource.getSalt(principle);
		member.setPassword(passwordEncoder.encodePassword(password, salt));
		return memberRepository.save(member);
	}

	@Override
	public Member register(final String login, final String password,
			final String email, final DateTime birthdate) {
		return createNewCustomer(login, password, email, birthdate);
	}
}
