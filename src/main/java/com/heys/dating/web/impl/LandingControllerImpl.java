package com.heys.dating.web.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.heys.dating.web.LandingController;
import com.heys.dating.web.dto.Registration;
import com.heys.dating.web.util.SecurityUtils;

@Controller
public class LandingControllerImpl implements LandingController {
	// private static final Logger logger = Logger
	// .getLogger(LandingController.class.getName());

	@Override
	public ModelAndView getDefaultLandingPage(final HttpServletRequest request) {
		if (SecurityUtils.isLoggedIn())
			return new ModelAndView("redirect:/dashboard");
		return new ModelAndView("landing", "command", new Registration());
	}
}
