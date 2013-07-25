package com.heys.dating.web.util;

import org.springframework.security.core.context.SecurityContextHolder;

import com.heys.dating.impl.gae.security.DatingUserDetails;

public class SecurityUtils {

	public static DatingUserDetails getCurrentUser() {
		try {
			return (DatingUserDetails) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
		} catch (final ClassCastException e) {
			return null;
		}
	}

	public static boolean isLoggedIn() {
		final Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		return null != principal && principal instanceof DatingUserDetails;
	}

}
