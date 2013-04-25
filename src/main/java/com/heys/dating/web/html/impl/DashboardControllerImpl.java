package com.heys.dating.web.html.impl;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.heys.dating.domain.repository.MemberRepository;
import com.heys.dating.domain.user.Member;
import com.heys.dating.util.UrlUtil;
import com.heys.dating.web.html.DashboardController;

@Controller("DashboardController")
public class DashboardControllerImpl implements DashboardController {

	@Autowired
	private MemberRepository memberRepository;

	@Override
	@Secured("isAuthenticated() and hasRole('ROLE_USER')")
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getDashboardPage(final Principal principal) {
		final ModelAndView mav = new ModelAndView("dashboard");

		final Member member = memberRepository.findByLogin(principal.getName());
		mav.getModelMap().put("user", member);

		mav.getModelMap().put(
				"profileIconUrl",
				UrlUtil.getProfileIconUrl(member.getProfile()
						.getProfilePicture(), member.getGender()));

		mav.getModelMap().put(
				"profilePreviewUrl",
				UrlUtil.getProfilePreviewUrl(member.getProfile()
						.getProfilePicture(), member.getGender()));

		return mav;
	}

}
