package com.heys.dating.domain.repository.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.heys.dating.domain.AbstractRepository;
import com.heys.dating.domain.member.Member;
import com.heys.dating.domain.repository.MemberRepository;

@Repository
public class MemberDatastoreRepository extends AbstractRepository<Member>
		implements MemberRepository {

	public MemberDatastoreRepository() {
		super(Member.class);
	}

	@Override
	public Member findByEmail(final String email) {
		return load().filter("emailIgnoreCase", StringUtils.upperCase(email))
				.first().now();
	}

	@Override
	public List<Member> findByLogin(final List<String> logins) {
		return load().filter("loginIgnoreCase",
				Lists.transform(logins, new Function<String, String>() {
					@Override
					public String apply(final String login) {
						return StringUtils.upperCase(login);
					}
				})).list();
	}

	@Override
	public Member findByLogin(final String login) {
		return load().filter("loginIgnoreCase", StringUtils.upperCase(login))
				.first().now();
	}
}
