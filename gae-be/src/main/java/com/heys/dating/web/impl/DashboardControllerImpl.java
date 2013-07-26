package com.heys.dating.web.impl;

import java.security.Principal;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.heys.dating.member.MemberService;
import com.heys.dating.message.MessageService;
import com.heys.dating.web.DashboardController;

@Controller("DashboardController")
public class DashboardControllerImpl implements DashboardController {
	private static final Logger logger = Logger
			.getLogger(DashboardControllerImpl.class.getName());

	@Autowired
	private MemberService memberSvc;

	@Autowired
	private MessageService messageSvc;

	@Override
	@Secured("isAuthenticated() and hasRole('ROLE_USER')")
	public ModelAndView getDashboardPage(final Principal principal) {
		final ModelAndView mav = new ModelAndView("dashboard");

		final String login = principal.getName();
		logger.info("Retrieving dashboard page for " + login);
		mav.getModelMap().put("login", login);

		return mav;
	}
}
