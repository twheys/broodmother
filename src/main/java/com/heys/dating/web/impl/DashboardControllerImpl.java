package com.heys.dating.web.impl;

import java.security.Principal;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.heys.dating.manager.MemberManager;
import com.heys.dating.manager.MessageManager;
import com.heys.dating.web.DashboardController;

@Controller("DashboardController")
public class DashboardControllerImpl implements DashboardController {
	private static final Logger logger = Logger
			.getLogger(DashboardControllerImpl.class.getName());

	@Autowired
	private MemberManager memberManager;

	@Autowired
	private MessageManager messageManager;

	@Override
	@Secured("isAuthenticated() and hasRole('ROLE_USER')")
	public ModelAndView getDashboardPage(final Principal principal) {
		final ModelAndView mav = new ModelAndView("dashboard");

		final String login = principal.getName();
		logger.info("Retrieving dashboard page for " + login);
		mav.getModelMap().put("login", login);

		// final Member member = memberManager.findByLoginOrEmail(login);
		// final Collection<Thread> threads =
		// messageManager.getNewThreadsForMember(member, 3,
		// 0);
		// mav.getModelMap().put("messages", threads);

		return mav;
	}
}
