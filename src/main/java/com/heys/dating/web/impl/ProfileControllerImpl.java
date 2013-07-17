package com.heys.dating.web.impl;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.googlecode.objectify.Key;
import com.heys.dating.domain.member.Member;
import com.heys.dating.domain.member.Profile;
import com.heys.dating.manager.MemberManager;
import com.heys.dating.manager.impl.NotFoundException;
import com.heys.dating.util.SecurityUtils;
import com.heys.dating.web.ProfileContent;
import com.heys.dating.web.ProfileController;
import com.heys.dating.web.ProfileDetails;
import com.heys.dating.web.ProfilePicture;
import com.heys.dating.web.ProfileUpdate;
import com.heys.dating.web.exception.InternalServerException;
import com.heys.dating.web.exception.ResourceNotFoundException;

@Controller("ProfileController")
public class ProfileControllerImpl implements ProfileController {
	private static final Logger logger = LoggerFactory
			.getLogger(ProfileController.class);

	@Autowired
	private MemberManager memberService;

	private ModelAndView createCreateProfileForm(final ProfileContent bean) {
		return new ModelAndView("create-profile-content", "command", bean);
	}

	private ModelAndView createCreateProfileForm(final ProfileDetails bean) {
		return new ModelAndView("create-profile-details", "command", bean);
	}

	private ModelAndView createCreateProfileForm(final ProfilePicture bean) {
		return new ModelAndView("create-profile-picture", "command", bean);
	}

	private ModelAndView createCreateProfileForm(final ProfileUpdate data) {
		if (data instanceof ProfileDetails)
			return createCreateProfileForm((ProfileDetails) data);
		if (data instanceof ProfileContent)
			return createCreateProfileForm((ProfileContent) data);
		if (data instanceof ProfilePicture)
			return createCreateProfileForm((ProfilePicture) data);
		return null;
	}

	private ModelAndView createCreateProfileForm(final ProfileUpdate data,
			final List<ObjectError> allErrors) {
		final ModelAndView mav = createCreateProfileForm(data);
		for (final ObjectError error : allErrors) {
			mav.addObject(error.getObjectName(), error.getCode());
		}
		return mav;
	}

	private ModelAndView createCreateProfileForm(final ProfileUpdate data,
			final Set<ConstraintViolation<?>> violations) {
		final ModelAndView mav = createCreateProfileForm(data);
		for (final ConstraintViolation<?> violation : violations) {
			final String field = violation.getPropertyPath().toString();
			final String errorCode = violation.getMessageTemplate();
			mav.getModel().put(field, errorCode);
		}
		return mav;
	}

	@Override
	@RequestMapping(value = "/profile/create", method = RequestMethod.GET)
	public ModelAndView createProfile() {
		try {
			final Key<Member> memberKey = getAuthMemberKey();
			final Profile profile = memberService.getProfileForMember(memberKey);
			return renderViewForRegistratonStep(profile);
		} catch (final NotFoundException e) {
			throw new InternalServerException(e);
		}
	}

	@Override
	@RequestMapping(value = "/profile/create/step2", method = RequestMethod.POST)
	public ModelAndView createProfile(final ProfileContent data,
			final BindingResult result) {
		logger.debug("Profile " + data.toString());
		if (result.hasErrors()) {
			logger.debug("Profile errors" + result.getAllErrors());
			return createCreateProfileForm(data, result.getAllErrors());
		}

		try {
			memberService.updateProfileContent(getAuthMemberKey(),
					data.getDescription());

			return new ModelAndView("redirect:/profile/create");
		} catch (final NotFoundException e) {
			throw new InternalServerException(e);
		} catch (final ConstraintViolationException e) {
			return createCreateProfileForm(data, e.getConstraintViolations());
		}
	}

	@Override
	@RequestMapping(value = "/profile/create/step1", method = RequestMethod.POST)
	public ModelAndView createProfile(final ProfileDetails data,
			final BindingResult result) {
		logger.debug("Profile " + data.toString());
		if (result.hasErrors()) {
			logger.debug("Profile errors" + result.getAllErrors());
			return createCreateProfileForm(data, result.getAllErrors());
		}

		try {
			memberService.updateProfileDetails(getAuthMemberKey(),
					data.getStatus(), data.getGender(), data.getZipCode(),
					data.getPartnerAgeMin(), data.getPartnerAgeMax(),
					data.getPartnerGenders());

			return new ModelAndView("redirect:/profile/create");
		} catch (final NotFoundException e) {
			throw new InternalServerException(e);
		} catch (final ConstraintViolationException e) {
			return createCreateProfileForm(data, e.getConstraintViolations());
		}
	}

	@Override
	@RequestMapping(value = "/profile/create/step3", method = RequestMethod.POST)
	public ModelAndView createProfile(final ProfilePicture data,
			final BindingResult result) {
		logger.debug("Profile " + data.toString());
		if (result.hasErrors()) {
			logger.debug("Profile errors" + result.getAllErrors());
			return createCreateProfileForm(data, result.getAllErrors());
		}

		getAuthMemberKey();

		try {
			// memberService.addPicture(profile, data.getPictureId(),
			// data.isProfilePicture(), data.getGallery(),
			// data.getDescription(), data.getVisibility());

			return new ModelAndView("redirect:/profile/create");
		} catch (final ConstraintViolationException e) {
			return createCreateProfileForm(data, e.getConstraintViolations());
		}
	}

	private Key<Member> getAuthMemberKey() {
		return SecurityUtils.getCurrentUser().getMemberKey();
	}

	@Override
	public ModelAndView getProfile(final String vanity) {
		final ModelAndView mav = new ModelAndView("profile");
		try {
			final Profile profile = memberService.getProfileForVanity(vanity);
			mav.getModelMap().put("profile", profile);
			return mav;
		} catch (final NotFoundException e) {
			throw new ResourceNotFoundException();
		}
	}

	private ModelAndView renderViewForRegistratonStep(final Profile profile) {
		switch (profile.getProfileCompletion()) {
		case STEP1_MISSING_BASIC_DETAILS:
			final ProfileDetails details = new ProfileDetails();
			return createCreateProfileForm(details);
		case STEP2_MISSING_PROFILE:
			final ProfileContent content = new ProfileContent();
			return createCreateProfileForm(content);
		case STEP3_MISSING_PICTURE:
			final ProfilePicture picture = new ProfilePicture();
			return createCreateProfileForm(picture);
		default:
		case IGNORE:
			return new ModelAndView("redirect:/dashboard");
		}
	}

}
