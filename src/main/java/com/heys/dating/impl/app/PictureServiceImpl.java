package com.heys.dating.impl.app;

import java.io.InputStream;

import javax.validation.ConstraintViolationException;

import org.springframework.stereotype.Service;

import com.heys.dating.picture.Picture;
import com.heys.dating.picture.PictureService;
import com.heys.dating.picture.PictureVisibility;
import com.heys.dating.profile.Profile;

@Service
public class PictureServiceImpl implements PictureService {

	@Override
	public Picture addPicture(final Profile profileKey,
			final InputStream stream, final String contentType,
			final String description) throws ConstraintViolationException,
			NotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Picture addPicture(final Profile profileKey,
			final InputStream stream, final String contentType,
			final String gallery, final String description,
			final PictureVisibility visibility)
			throws ConstraintViolationException, NotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void movePicture(final Picture picture, final String gallery) {
		// TODO Auto-generated method stub

	}

	@Override
	public void useAsProfilePicture(final Profile profile, final Picture picture) {
		// TODO Auto-generated method stub

	}

}
