package com.heys.dating.domain.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

public class Gallery {
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
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
