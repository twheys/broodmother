package com.heys.dating.web.html;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.heys.dating.web.html.dto.Registration;

@RequestMapping(value = "/register")
public interface RegistrationController {
	@RequestMapping(method = RequestMethod.GET)
	ModelAndView getRegistrationPage();

	@RequestMapping(value = "/success", method = RequestMethod.GET)
	ModelAndView getRegistrationSuccessPage(HttpSession session);

	@RequestMapping(method = RequestMethod.POST)
	ModelAndView submitRegistration(
			@ModelAttribute("registration") @Valid Registration registration,
			BindingResult result, HttpSession session);

}
