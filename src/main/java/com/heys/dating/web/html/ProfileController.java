package com.heys.dating.web.html;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping(value = "/profile")
public interface ProfileController {
	@RequestMapping(value = "/{vanity}", method = RequestMethod.GET)
	ModelAndView getProfile(@PathVariable String vanity);

}
