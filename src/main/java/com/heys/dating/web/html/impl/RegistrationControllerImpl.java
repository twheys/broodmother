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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.heys.dating.domain.repository.CustomerRepository;
import com.heys.dating.domain.user.Customer;
import com.heys.dating.service.CustomerManager;
import com.heys.dating.web.html.RegistrationController;
import com.heys.dating.web.html.dto.Registration;

@Controller("RegistrationController")
public class RegistrationControllerImpl implements RegistrationController {
	private static final Logger logger = Logger
			.getLogger(RegistrationControllerImpl.class.getName());

	private static final DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

	@Autowired
	private CustomerManager customerManager;

	@Autowired
	private CustomerRepository customerRepository;

	public RegistrationControllerImpl() {
		logger.info("Starting Registration Controller");
	}

	private ModelAndView createRegistrationForm() {
		return createRegistrationForm(null, null);
	}

	private ModelAndView createRegistrationForm(
			final Registration registration, final List<ObjectError> errors) {
		final Map<String, Object> data = new HashMap<String, Object>();

		if (null == registration) {
			data.put("command", new Registration());
		} else {
			data.put("command", registration);
		}

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
	public ModelAndView getRegistrationPage() {
		return createRegistrationForm();
	}

	@Override
	public ModelAndView getRegistrationSuccessPage(
			@PathVariable final String login) {
		final Customer customer = customerRepository
				.findByLoginIgnoreCase(login);
		if (null == customer) {
			logger.warning("Customer reached login success page for a non existing login: "
					+ login);
			return new ModelAndView("redirect:/register");
		}

		final Map<String, Object> data = new HashMap<String, Object>();
		data.put("customer", customer);
		return new ModelAndView("register-success", data);
	}

	@Override
	public ModelAndView submitRegistration(final Registration data,
			final BindingResult result) {
		if (result.hasErrors()) {
			logger.finest("Registration errors" + result.getAllErrors());
			return createRegistrationForm(data, result.getAllErrors());
		}

		final Date birthdate = formatDate(data.getBirthdateDD(),
				data.getBirthdateMM(), data.getBirthdateYYYY());
		final Customer customer = customerManager.register(data.getLogin(),
				data.getPassword(), data.getEmail(), birthdate);
		return new ModelAndView("redirect:/register/" + customer.getLogin());
	}

}
