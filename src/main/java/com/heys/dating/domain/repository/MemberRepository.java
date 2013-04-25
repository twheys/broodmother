package com.heys.dating.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.google.appengine.api.datastore.Key;
import com.heys.dating.domain.user.Member;

@Transactional
public interface MemberRepository extends JpaRepository<Member, Key> {
	Member findByEmailIgnoreCase(String email);

	Member findByLogin(String name);

	Member findByLoginIgnoreCase(String login);

	Member findByLoginOrEmailIgnoreCase(String value);
}
