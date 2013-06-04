package com.heys.dating.web.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.heys.dating.domain.member.Picture;
import com.heys.dating.service.PictureManager;
import com.heys.dating.web.PictureController;
import com.heys.dating.web.exception.ResourceNotFoundException;

public class PictureControllerImpl implements PictureController {
	@Autowired
	private PictureManager pictureManager;

	@Override
	public byte[] viewFullProfilePng(final String login) {
		final Picture picture = pictureManager.getProfilePicture(login);
		if (null == picture)
			// TODO return default picture for user
			throw new ResourceNotFoundException();
		return picture.getFull().getBytes();
	}

	@Override
	public byte[] viewIconProfilePng(final String login) {
		final Picture picture = pictureManager.getProfilePicture(login);
		if (null == picture)
			// TODO return default picture for user
			throw new ResourceNotFoundException();
		return picture.getIcon().getBytes();
	}

	@Override
	public byte[] viewPreviewProfilePng(final String login) {
		final Picture picture = pictureManager.getProfilePicture(login);
		if (null == picture)
			// TODO return default picture for user
			throw new ResourceNotFoundException();
		return picture.getPreview().getBytes();
	}

}
