package com.heys.dating.impl.gae.repository;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.heys.dating.member.Member;
import com.heys.dating.member.MemberRepository;

@Repository
public class MemberRepositoryDatastoreImpl extends
		AbstractDatastoreRepository<Member> implements MemberRepository {

	public MemberRepositoryDatastoreImpl() {
		super(Member.class);
	}

	@Override
	public Member findByEmail(final String email) {
		return load().filter("emailIgnoreCase", StringUtils.upperCase(email))
				.first().now();
	}

	@Override
	public List<Member> findByLogin(final List<String> logins) {
		return load().filter("loginIgnoreCase in",
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
