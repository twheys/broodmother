package com.heys.dating.web.html.impl;

import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;

import com.heys.dating.domain.user.Member;
import com.heys.dating.service.MemberService;
import com.heys.dating.web.html.RegistrationController;
import com.heys.dating.web.html.dto.Registration;

@Controller("RegistrationController")
public class RegistrationControllerImpl implements RegistrationController {
	private static final Logger logger = Logger
			.getLogger(RegistrationController.class.getName());

	@Autowired
	private MemberService memberService;

	public RegistrationControllerImpl() {
		logger.info("Starting Registration Controller");
	}

	private ModelAndView createRegistrationForm() {
		return createRegistrationForm(null, null);
	}

	private ModelAndView createRegistrationForm(
			final Registration registration, final List<ObjectError> errors) {

		if (null != errors) {

		}
		final Registration bean = null == registration ? new Registration()
				: registration;
		return new ModelAndView("register", "command", bean);
	}

	@Override
	public ModelAndView getRegistrationPage() {
		return createRegistrationForm();
	}

	@Override
	public ModelAndView getRegistrationSuccessPage(final HttpSession session) {
		final Object member = session.getAttribute("member");
		session.removeAttribute("member");

		if (null == member) {
			logger.warning("Customer reached login success page for a non existing login");
			return new ModelAndView("redirect:/register");
		}

		return new ModelAndView("confirmation", "member", member);
	}

	@Override
	public ModelAndView submitRegistration(final Registration data,
			final BindingResult result, final HttpSession session) {
		logger.fine("Registration " + data.toString());
		if (result.hasErrors()) {
			logger.finest("Registration errors" + result.getAllErrors());
			return createRegistrationForm(data, result.getAllErrors());
		}

		final DateTime birthdate = new DateTime(data.getBirthyear(),
				data.getBirthmonth(), data.getBirthday(), 0, 0, 0, 0);
		final Member member = memberService.register(data.getLogin(),
				data.getPassword(), data.getEmail(), birthdate);

		session.setAttribute("member", member);
		return new ModelAndView("redirect:/register/confirm");
	}

}
