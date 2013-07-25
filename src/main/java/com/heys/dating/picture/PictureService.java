package com.heys.dating.picture;

import java.io.InputStream;

import javax.validation.ConstraintViolationException;

import com.googlecode.objectify.Key;
import com.heys.dating.impl.app.NotFoundException;
import com.heys.dating.profile.Profile;

public interface PictureService {

	void addPicture(final Key<Profile> profileKey, final InputStream stream,
			final String contentType, final String description)
			throws ConstraintViolationException, NotFoundException;

	void addPicture(final Key<Profile> profileKey, final InputStream stream,
			final String contentType, final boolean isProfilePicture,
			final String gallery, final String description,
			final PictureVisibility visibility)
			throws ConstraintViolationException, NotFoundException;
}
