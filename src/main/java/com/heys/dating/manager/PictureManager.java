package com.heys.dating.manager;

import java.io.InputStream;

import javax.validation.ConstraintViolationException;

import com.googlecode.objectify.Key;
import com.heys.dating.domain.member.PictureVisibility;
import com.heys.dating.domain.member.Profile;
import com.heys.dating.manager.impl.NotFoundException;

public interface PictureManager {

	void addPicture(final Key<Profile> profileKey, final InputStream stream,
			final String contentType, final String description)
			throws ConstraintViolationException, NotFoundException;

	void addPicture(final Key<Profile> profileKey, final InputStream stream,
			final String contentType, final boolean isProfilePicture,
			final String gallery, final String description,
			final PictureVisibility visibility)
			throws ConstraintViolationException, NotFoundException;
}
