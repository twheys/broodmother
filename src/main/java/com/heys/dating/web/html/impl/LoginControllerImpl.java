package com.heys.dating.web.html.impl;

import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.heys.dating.web.html.LoginController;

@Controller("LoginController")
public class LoginControllerImpl implements LoginController {
	private static final Logger logger = Logger
			.getLogger(LoginControllerImpl.class.getName());

	@Override
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(final ModelMap model) {
		logger.info("Login stuff: " + model.toString());
		return new ModelAndView("login", model);
	}

	@Override
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(final ModelMap model) {
		return new ModelAndView("logout", model);
	}

}
