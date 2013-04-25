package com.heys.dating.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.thymeleaf.util.SetUtils;

import com.heys.dating.domain.user.Member;

public class DatingUserDetails extends User implements UserDetails {

	private static final long serialVersionUID = 3491485521549818126L;

	private static Collection<? extends GrantedAuthority> getAuthorities(
			final Set<String> privileges) {
		if (null == privileges)
			return new ArrayList<GrantedAuthority>();

		final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (final String privilege : privileges) {
			authorities.add(new SimpleGrantedAuthority(privilege));
		}
		return authorities;
	}

	private final String salt;

	public DatingUserDetails() {
		super("tim", "test{timThu Apr 25 17:49:41 CEST 2013}", true, true,
				false, false, getAuthorities((Set<String>) SetUtils
						.toSet("ROLE_USER")));
		salt = "{timThu Apr 25 17:49:41 CEST 2013}";
	}

	public DatingUserDetails(final Member customer) {
		super(customer.getLogin(), customer.getPassword(),
				customer.isEnabled(), true, !customer.hasExpiredCredentials(),
				!customer.isLocked(), getAuthorities(customer.getPrivileges()));

		salt = customer.getLogin() + customer.getCreationDate().toString();
	}

	public String getSalt() {
		return salt;
	}
}