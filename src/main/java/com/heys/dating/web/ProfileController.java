package com.heys.dating.web;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping(value = "/profile")
public interface ProfileController {

	@RequestMapping(value = "/profile/create", method = RequestMethod.GET)
	ModelAndView createProfile();

	@RequestMapping(value = "/profile/create/step2", method = RequestMethod.POST)
	ModelAndView createProfile(@ModelAttribute("content") ProfileContent data,
			BindingResult result);

	@RequestMapping(value = "/profile/create/step1", method = RequestMethod.POST)
	ModelAndView createProfile(@ModelAttribute("details") ProfileDetails data,
			BindingResult result);

	@RequestMapping(value = "/profile/create/step3", method = RequestMethod.POST)
	ModelAndView createProfile(@ModelAttribute("picture") ProfilePicture data,
			BindingResult result);

	@RequestMapping(value = "/{vanity}", method = RequestMethod.GET)
	ModelAndView getProfile(@PathVariable String vanity);

}
