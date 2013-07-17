package com.heys.dating.domain.repository;

import java.util.List;

import com.heys.dating.domain.Repository;
import com.heys.dating.domain.member.Member;

public interface MemberRepository extends Repository<Member> {
	Member findByEmail(final String email);

	List<Member> findByLogin(final List<String> logins);

	Member findByLogin(final String login);
}
