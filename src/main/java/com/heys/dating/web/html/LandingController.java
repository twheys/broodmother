package com.heys.dating.web.html;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping(value = "/")
public interface LandingController {

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getDefaultLandingPage(HttpServletRequest request);
}
