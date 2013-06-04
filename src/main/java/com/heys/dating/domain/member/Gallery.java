package com.heys.dating.domain.member;

import java.util.List;

import com.google.common.collect.Lists;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.heys.dating.domain.AbstractVersionedEntity;

@Entity
@Cache
public class Gallery extends AbstractVersionedEntity {
	private static final long serialVersionUID = 7282661284457916312L;

	private String name;
	private List<Key<Picture>> pictures;

	public Gallery() {
		pictures = Lists.newArrayList();
	}

	public String getName() {
		return name;
	}

	public List<Key<Picture>> getPictures() {
		return pictures;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setPictures(final List<Key<Picture>> pictures) {
		this.pictures = pictures;
	}
}
