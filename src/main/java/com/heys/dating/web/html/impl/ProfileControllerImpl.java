package com.heys.dating.web.html.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.heys.dating.domain.user.Profile;
import com.heys.dating.service.MemberService;
import com.heys.dating.web.exception.ResourceNotFoundException;
import com.heys.dating.web.html.ProfileController;

@Controller("ProfileController")
public class ProfileControllerImpl implements ProfileController {

	@Autowired
	private MemberService memberService;

	@Override
	public ModelAndView getProfile(@PathVariable final String vanity) {
		final ModelAndView mav = new ModelAndView("profile");
		final Profile profile = memberService.getProfileForVanity(vanity);
		if (null == profile)
			throw new ResourceNotFoundException();
		mav.getModelMap().put("profile", profile);
		return mav;
	}

}
