package com.heys.dating.service;

import java.security.Principal;

import com.heys.dating.domain.user.Picture;

public interface PictureManager {

	Picture getPicture(String pictureKey, Principal auth);

	void setPictureStatus(String pictureKey, Principal auth, String status);

	void setPictureToDeleted(String pictureKey, Principal auth);

	Picture storePicture(Principal auth, byte[] file);
}
