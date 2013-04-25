package com.heys.dating.web.html.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.heys.dating.util.SecurityUtils;
import com.heys.dating.web.html.LandingController;
import com.heys.dating.web.html.dto.Registration;

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
