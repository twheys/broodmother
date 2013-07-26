package com.heys.dating.web;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.heys.dating.web.dto.Registration;

@RequestMapping(value = "/register")
public interface RegistrationController {
	@RequestMapping(method = RequestMethod.GET)
	ModelAndView getRegistrationPage();

	@RequestMapping(value = "/confirm", method = RequestMethod.GET)
	ModelAndView getRegistrationSuccessPage(HttpSession session);

	@RequestMapping(method = RequestMethod.POST)
	ModelAndView submitRegistration(
			@ModelAttribute("registration") Registration registration,
			BindingResult result, HttpSession session, Locale locale);

}
