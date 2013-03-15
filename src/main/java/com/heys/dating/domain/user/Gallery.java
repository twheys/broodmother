package com.heys.dating.domain.user;

import java.util.ArrayList;
import java.util.List;

public class Gallery {
	private List<Picture> pictures;

	public Gallery() {
		pictures = new ArrayList<Picture>();
	}

	public List<Picture> getPictures() {
		return pictures;
	}

	public void setPictures(final List<Picture> pictures) {
		this.pictures = pictures;
	}
}
