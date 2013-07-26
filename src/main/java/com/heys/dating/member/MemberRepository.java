package com.heys.dating.member;

import java.util.List;

import com.heys.dating.Repository;

public interface MemberRepository extends Repository<Member> {
	Member findByEmail(final String email);

	List<Member> findByLogin(final List<String> logins);

	Member findByLogin(final String login);
}
