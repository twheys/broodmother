package com.heys.dating.web.impl;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;

import com.google.appengine.repackaged.org.joda.time.DateMidnight;
import com.heys.dating.member.Member;
import com.heys.dating.member.MemberService;
import com.heys.dating.web.RegistrationController;
import com.heys.dating.web.dto.Registration;

@Controller("RegistrationController")
public class RegistrationControllerImpl implements RegistrationController {
	private static final Logger logger = LoggerFactory
			.getLogger(RegistrationController.class);

	@Autowired
	private MemberService memberService;

	public RegistrationControllerImpl() {
		logger.info("Starting Registration Controller");
	}

	private ModelAndView createRegistrationForm(final Registration bean) {
		return new ModelAndView("register", "command", bean);
	}

	private ModelAndView createRegistrationForm(final Registration data,
			final List<ObjectError> allErrors) {
		final ModelAndView mav = createRegistrationForm(data);
		for (final ObjectError error : allErrors) {
			mav.addObject(error.getObjectName(), error.getCode());
		}
		return mav;
	}

	private ModelAndView createRegistrationForm(final Registration data,
			final Set<ConstraintViolation<?>> violations) {
		final ModelAndView mav = createRegistrationForm(data);
		for (final ConstraintViolation<?> violation : violations) {
			final String field = violation.getPropertyPath().toString();
			final String errorCode = violation.getMessageTemplate();
			mav.getModel().put(field, errorCode);
		}
		return mav;
	}

	@Override
	public ModelAndView getRegistrationPage() {
		return createRegistrationForm(new Registration());
	}

	@Override
	public ModelAndView getRegistrationSuccessPage(final HttpSession session) {
		final Member member = (Member) session.getAttribute("member");
		session.removeAttribute("member");

		if (null == member) {
			logger.warn("Customer reached login success page for a non existing login");
			return new ModelAndView("redirect:/register");
		}

		return new ModelAndView("confirmation", "member", member);
	}

	@Override
	public ModelAndView submitRegistration(final Registration data,
			final BindingResult result, final HttpSession session,
			final Locale locale) {
		logger.debug("Registration " + data.toString());
		if (result.hasErrors()) {
			logger.debug("Registration errors" + result.getAllErrors());
			return createRegistrationForm(data, result.getAllErrors());
		}

		final DateMidnight birthdate = new DateMidnight(data.getBirthyear(),
				data.getBirthmonth(), data.getBirthday());
		try {
			final Member member = memberService.register(data.getLogin(),
					data.getPassword(), data.getEmail(), birthdate, locale);

			session.setAttribute("member", member);
			return new ModelAndView("redirect:/register/confirm");
		} catch (final ConstraintViolationException e) {
			return createRegistrationForm(data, e.getConstraintViolations());
		}
	}

}
