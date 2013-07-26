package com.heys.dating.picture;

import java.io.InputStream;

import javax.validation.ConstraintViolationException;

import com.heys.dating.impl.app.NotFoundException;
import com.heys.dating.profile.Profile;

public interface PictureService {

	Picture addPicture(final Profile profileKey, final InputStream stream,
			final String contentType, final String description)
			throws ConstraintViolationException, NotFoundException;

	Picture addPicture(final Profile profileKey, final InputStream stream,
			final String contentType, final String gallery,
			final String description, final PictureVisibility visibility)
			throws ConstraintViolationException, NotFoundException;

	void movePicture(final Picture picture, final String gallery);

	void useAsProfilePicture(final Profile profile, final Picture picture);

}
