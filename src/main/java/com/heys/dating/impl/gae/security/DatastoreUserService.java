package com.heys.dating.impl.gae.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.heys.dating.member.Member;
import com.heys.dating.member.MemberService;

@Component
public class DatastoreUserService implements UserDetailsService {
	private static final Logger logger = LoggerFactory
			.getLogger(DatastoreUserService.class);

	@Autowired
	private MemberService memberSvc;

	@Override
	public UserDetails loadUserByUsername(final String identifier)
			throws UsernameNotFoundException {
		logger.info("Fetching member for authentication. :: " + identifier);

		final Member member = memberSvc.findByLoginOrEmail(identifier);
		if (null == member) {
			logger.info("Member does not exist, authorization failed. :: "
					+ identifier);
			throw new UsernameNotFoundException(identifier);
		}
		logger.info("Retrieved member. Attempting authorization. :: "
				+ identifier);
		return new DatingUserDetails(member);
	}
}
