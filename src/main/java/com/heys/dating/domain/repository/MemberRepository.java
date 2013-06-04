package com.heys.dating.domain.repository;

import java.util.List;

import com.heys.dating.domain.Repository;
import com.heys.dating.domain.member.Member;

public interface MemberRepository extends Repository<Member> {
	Member findByEmail(String email);

	List<Member> findByLogin(List<String> logins);

	Member findByLogin(String login);
}
