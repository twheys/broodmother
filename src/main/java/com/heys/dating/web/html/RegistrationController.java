package com.heys.dating.web.html;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.heys.dating.web.html.dto.RegistrationForm;

@RequestMapping(value = "/register")
public interface RegistrationController {
	@RequestMapping(method = RequestMethod.GET)
	ModelAndView register();

	@RequestMapping(method = RequestMethod.POST)
	ModelAndView submitRegistration(
			@ModelAttribute("registration") RegistrationForm registration,
			BindingResult result);

}
