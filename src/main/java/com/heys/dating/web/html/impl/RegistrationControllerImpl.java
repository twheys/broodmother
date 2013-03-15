package com.heys.dating.web.html.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.heys.dating.domain.user.Customer;
import com.heys.dating.service.CustomerManager;
import com.heys.dating.web.html.RegistrationController;
import com.heys.dating.web.html.dto.RegistrationForm;

@Controller("RegistrationController")
public class RegistrationControllerImpl implements RegistrationController {
	private static final Logger logger = Logger
			.getLogger(RegistrationControllerImpl.class.getName());

	private static final DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

	@Autowired
	private CustomerManager customerManager;

	public RegistrationControllerImpl() {
		logger.info("Starting Registration Controller");
	}

	private ModelAndView createRegisterSuccessModelAndView(final String login,
			final String email) {
		final Map<String, Object> data = new HashMap<String, Object>();
		data.put("login", login);
		data.put("email", email);
		return new ModelAndView("registration-success", data);
	}

	private ModelAndView createRegistrationForm() {
		return createRegistrationForm(null);
	}

	private ModelAndView createRegistrationForm(final List<ObjectError> errors) {
		final Map<String, Object> data = new HashMap<String, Object>();
		data.put("command", new RegistrationForm());
		if (null != errors) {

		}
		return new ModelAndView("register", data);
	}

	private Date formatDate(final String birthdateDD, final String birthdateMM,
			final String birthdateYYYY) {
		try {
			return df.parse(new StringBuffer(birthdateDD).append('-')
					.append(birthdateMM).append('-').append(birthdateYYYY)
					.toString());
		} catch (final ParseException e) {
			return null;
		}
	}

	@Override
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		return createRegistrationForm();
	}

	@Override
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView submitRegistration(final RegistrationForm data,
			final BindingResult result) {
		if (result.hasErrors()) {
			logger.finest("Registration errors" + result.getAllErrors());
			return createRegistrationForm(result.getAllErrors());
		}

		final Date birthdate = formatDate(data.getBirthdateDD(),
				data.getBirthdateMM(), data.getBirthdateYYYY());
		final Customer customer = customerManager.register(data.getLogin(),
				data.getPassword(), data.getEmail(), birthdate);
		return createRegisterSuccessModelAndView(customer.getLogin(),
				customer.getEmail());
	}

}
