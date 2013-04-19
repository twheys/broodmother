package com.heys.dating.web.html.impl;

import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.heys.dating.web.html.LoginController;

@Controller("LoginController")
public class LoginControllerImpl implements LoginController {
	private static final Logger logger = Logger
			.getLogger(LoginControllerImpl.class.getName());

	@Override
	public ModelAndView login(final String error, final String userName,
			final ModelMap model) {
		if ("1".equals(error)) {
			logger.info("Failed to login. Redirecting to login page with error message.");
			model.addAttribute("error", "true");
			model.addAttribute("username", userName);
		}
		return new ModelAndView("login", model);
	}

	@Override
	public ModelAndView logout(final ModelMap model) {
		return new ModelAndView("logout", model);
	}

}
