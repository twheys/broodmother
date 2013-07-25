package com.heys.dating.impl.app;

import java.io.InputStream;

import javax.validation.ConstraintViolationException;

import org.springframework.stereotype.Service;

import com.googlecode.objectify.Key;
import com.heys.dating.picture.PictureService;
import com.heys.dating.picture.PictureVisibility;
import com.heys.dating.profile.Profile;

@Service
public class PictureServiceImpl implements PictureService {

	@Override
	public void addPicture(final Key<Profile> profileKey,
			final InputStream stream, final String contentType,
			final boolean isProfilePicture, final String gallery,
			final String description, final PictureVisibility visibility)
			throws ConstraintViolationException, NotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	public void addPicture(final Key<Profile> profileKey,
			final InputStream stream, final String contentType,
			final String description) throws ConstraintViolationException,
			NotFoundException {
		addPicture(profileKey, stream, contentType, false, "default", null,
				PictureVisibility.PUBLIC);
	}

}
