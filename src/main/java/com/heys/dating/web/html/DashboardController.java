package com.heys.dating.web.html;

import java.security.Principal;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping(value = "/dashboard")
public interface DashboardController {

	@Secured("isAuthenticated() and hasRole('ROLE_USER')")
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getDashboardPage(Principal principal);
}
