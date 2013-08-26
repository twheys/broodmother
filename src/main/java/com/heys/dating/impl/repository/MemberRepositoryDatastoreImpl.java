package com.heys.dating.impl.repository;

import static com.heys.dating.util.DatastoreUtil.c;

import java.util.List;

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
		return load().filter("emailIgnoreCase", c(email)).first().now();
	}

	@Override
	public List<Member> findByLogin(final List<String> logins) {
		return load().filter("loginIgnoreCase in",
				Lists.transform(logins, new Function<String, String>() {
					@Override
					public String apply(final String login) {
						return c(login);
					}
				})).list();
	}

	@Override
	public Member findByLogin(final String login) {
		return load().filter("loginIgnoreCase", c(login)).first().now();
	}
}
