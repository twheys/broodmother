package com.heys.dating.service;

import java.security.Principal;

import com.heys.dating.domain.member.Picture;

public interface PictureManager {

	// FIXME refactor this
	Picture getPicture(String pictureKey, Principal auth);

	Picture getProfilePicture(String login);

	Picture getStaticPicture(String id);

	// FIXME refactor this
	void setPictureStatus(String pictureKey, Principal auth, String status);

	// FIXME refactor this
	void setPictureToDeleted(String pictureKey, Principal auth);

	// FIXME refactor this
	Picture storePicture(Principal auth, byte[] file);
}
