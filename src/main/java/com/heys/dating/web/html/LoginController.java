package com.heys.dating.web.html;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

public interface LoginController {
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(ModelMap model);

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(ModelMap model);

}
