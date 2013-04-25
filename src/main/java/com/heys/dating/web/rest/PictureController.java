package com.heys.dating.web.rest;

import java.security.Principal;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.heys.dating.web.rest.dto.picture.CreatePictureResult;
import com.heys.dating.web.rest.dto.picture.DeletePictureResult;
import com.heys.dating.web.rest.dto.picture.PictureDTO;
import com.heys.dating.web.rest.dto.picture.Status;
import com.heys.dating.web.rest.dto.picture.UpdateStatusResult;

@RequestMapping(value = "/p")
public interface PictureController {
	@RequestMapping(value = "/{pictureKey}/delete", method = RequestMethod.GET, produces = "image/jpg")
	DeletePictureResult delete(Principal auth, @PathVariable String pictureKey);

	@RequestMapping(value = "/{pictureKey}", method = RequestMethod.PUT, consumes = "image/*")
	PictureDTO get(Principal auth, @PathVariable String pictureKey);

	@RequestMapping(value = "/status/{pictureKey}", method = RequestMethod.PUT, consumes = "image/*")
	UpdateStatusResult setStatus(Principal auth,
			@PathVariable String pictureKey, @PathVariable Status status);

	@RequestMapping(value = "/upload", method = RequestMethod.PUT, consumes = "image/*")
	CreatePictureResult upload(Principal auth, PictureDTO fileUpload);

	@RequestMapping(value = "/{pictureKey}/full.png", method = RequestMethod.GET, produces = "image/png")
	byte[] viewFullPng(Principal auth, @PathVariable String pictureKey);

	@RequestMapping(value = "/{pictureKey}/icon.png", method = RequestMethod.GET, produces = "image/png")
	byte[] viewIconPng(Principal auth, @PathVariable String pictureKey);

	@RequestMapping(value = "/{pictureKey}/preview.png", method = RequestMethod.GET, produces = "image/png")
	byte[] viewPreviewPng(Principal auth, @PathVariable String pictureKey);

}