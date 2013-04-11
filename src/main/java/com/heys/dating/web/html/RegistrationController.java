package com.heys.dating.web.html;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.heys.dating.web.html.dto.Registration;

@RequestMapping(value = "/register")
public interface RegistrationController {
	@RequestMapping(method = RequestMethod.GET)
	ModelAndView getRegistrationPage();

	@RequestMapping(value = "/{login}", method = RequestMethod.GET)
	ModelAndView getRegistrationSuccessPage(@PathVariable String login);

	@RequestMapping(method = RequestMethod.POST)
	ModelAndView submitRegistration(
			@ModelAttribute("registration") Registration registration,
			BindingResult result);

}
