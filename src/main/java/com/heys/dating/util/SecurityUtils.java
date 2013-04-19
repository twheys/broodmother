package com.heys.dating.util;

import org.springframework.security.core.context.SecurityContextHolder;

import com.heys.dating.security.DatingUserDetails;

public class SecurityUtils {

	public static boolean isLoggedIn() {
		final Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		return null != principal && principal instanceof DatingUserDetails;
	}

}
