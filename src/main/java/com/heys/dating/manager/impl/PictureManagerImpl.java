package com.heys.dating.manager.impl;

import java.io.InputStream;

import javax.validation.ConstraintViolationException;

import org.springframework.stereotype.Service;

import com.googlecode.objectify.Key;
import com.heys.dating.domain.member.PictureVisibility;
import com.heys.dating.domain.member.Profile;
import com.heys.dating.manager.PictureManager;

@Service
public class PictureManagerImpl implements PictureManager {

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
