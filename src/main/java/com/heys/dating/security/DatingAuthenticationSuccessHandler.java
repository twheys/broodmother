package com.heys.dating.security;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.heys.dating.service.MemberManager;
import com.heys.dating.service.impl.NotFoundException;

@Component("AuthenticationSuccessHandler")
public class DatingAuthenticationSuccessHandler implements
		AuthenticationSuccessHandler {
	private static final Logger logger = Logger
			.getLogger(DatingAuthenticationSuccessHandler.class.getName());
	@Autowired
	private MemberManager memberService;

	@Override
	public void onAuthenticationSuccess(final HttpServletRequest request,
			final HttpServletResponse response,
			final Authentication authentication) throws IOException,
			ServletException {
		final DatingUserDetails principle = (DatingUserDetails) authentication
				.getPrincipal();
		logger.info("Logging in user: " + principle);

		try {
			switch (memberService.isProfileComplete(principle
					.getMemberProfileKey())) {
			case IGNORE:
				response.sendRedirect("/dashboard");
				return;
			default:
				response.sendRedirect("/profile/create");
			}
		} catch (final NotFoundException e) {
			throw new ServletException(e);
		}
	}

}
