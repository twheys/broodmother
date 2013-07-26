package com.heys.dating.web.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import lombok.Getter;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.heys.dating.member.Member;
import com.heys.dating.member.MemberRole;

public class DatingUserDetails extends User implements UserDetails {

	private static final long serialVersionUID = 3491485521549818126L;

	private static Collection<? extends GrantedAuthority> getAuthorities(
			final Set<MemberRole> privileges) {
		if (null == privileges)
			return new ArrayList<GrantedAuthority>();

		final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (final MemberRole privilege : privileges) {
			authorities.add(new SimpleGrantedAuthority(privilege.name()));
		}
		return authorities;
	}

	@Getter
	private final Member member;

	@Getter
	private final String salt;

	public DatingUserDetails(final Member member) {
		super(member.getLogin(), member.getPassword(), member.isEnabled(),
				true, !member.isHasExpiredCredentials(), !member.isLocked(),
				getAuthorities(member.getPrivileges()));

		final String saltContents = member.getLogin()
				+ member.getCreationDate().toString();

		this.member = member;
		salt = DigestUtils.md5Hex(saltContents);
	}
}