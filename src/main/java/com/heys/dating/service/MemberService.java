package com.heys.dating.service;

import org.joda.time.DateTime;

import com.heys.dating.domain.user.Member;

public interface MemberService {

	Member register(String login, String password, String email,
			DateTime birthdate);

}
