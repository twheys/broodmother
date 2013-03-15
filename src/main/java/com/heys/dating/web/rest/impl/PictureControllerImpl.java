package com.heys.dating.web.rest.impl;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;

import com.heys.dating.domain.user.Picture;
import com.heys.dating.service.PictureManager;
import com.heys.dating.web.rest.PictureController;
import com.heys.dating.web.rest.dto.exception.ResourceNotFoundException;
import com.heys.dating.web.rest.dto.picture.CreatePictureResult;
import com.heys.dating.web.rest.dto.picture.DeletePictureResult;
import com.heys.dating.web.rest.dto.picture.PictureDTO;
import com.heys.dating.web.rest.dto.picture.Status;
import com.heys.dating.web.rest.dto.picture.UpdateStatusResult;

public class PictureControllerImpl implements PictureController {

	@Autowired
	private PictureManager pictureManager;

	@Override
	public DeletePictureResult delete(final Principal auth,
			final String pictureKey) {
		pictureManager.setPictureToDeleted(pictureKey, auth);
		return new DeletePictureResult(pictureKey);
	}

	private Picture doGetPicture(final Principal auth, final String pictureKey)
			throws ResourceNotFoundException {
		final Picture picture = pictureManager.getPicture(pictureKey, auth);
		if (null == picture) {
			throw new ResourceNotFoundException();
		}
		return picture;
	}

	@Override
	public PictureDTO get(final Principal auth, final String pictureKey) {
		return new PictureDTO(doGetPicture(auth, pictureKey));
	}

	@Override
	public UpdateStatusResult setStatus(final Principal auth,
			final String pictureKey, final Status status) {
		pictureManager.setPictureStatus(pictureKey, auth, status.toString());
		return new UpdateStatusResult(pictureKey);
	}

	@Override
	public CreatePictureResult upload(final Principal auth,
			final PictureDTO fileUpload) {
		final Picture picture = pictureManager.storePicture(auth,
				fileUpload.getFile());
		return new CreatePictureResult(picture.getKey().getName());
	}

	@Override
	public byte[] viewFullJpg(final Principal auth, final String pictureKey) {
		return doGetPicture(auth, pictureKey).getFull().getBytes();
	}

	@Override
	public byte[] viewFullPng(final Principal auth, final String pictureKey) {
		return doGetPicture(auth, pictureKey).getFull().getBytes();
	}

	@Override
	public byte[] viewPreviewJpg(final Principal auth, final String pictureKey) {
		return doGetPicture(auth, pictureKey).getPreview().getBytes();
	}

	@Override
	public byte[] viewPreviewPng(final Principal auth, final String pictureKey) {
		return doGetPicture(auth, pictureKey).getPreview().getBytes();
	}
}