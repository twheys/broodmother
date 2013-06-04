package com.heys.dating.domain.member;

import com.google.appengine.api.datastore.ShortBlob;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.heys.dating.domain.AbstractVersionedEntity;

@Entity
@Cache
public class Picture extends AbstractVersionedEntity {
	private static final long serialVersionUID = 8418671229417665202L;

	private ShortBlob full;
	private ShortBlob preview;
	private ShortBlob icon;
	private String description;
	private PictureType type;
	private FileType fileType;
	private PictureStatus state;

	public String getDescription() {
		return description;
	}

	public FileType getFileType() {
		return fileType;
	}

	public ShortBlob getFull() {
		return full;
	}

	public ShortBlob getIcon() {
		return icon;
	}

	public ShortBlob getPreview() {
		return preview;
	}

	public PictureStatus getState() {
		return state;
	}

	public PictureType getType() {
		return type;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setFileType(final FileType fileType) {
		this.fileType = fileType;
	}

	public void setFull(final ShortBlob full) {
		this.full = full;
	}

	public void setIcon(final ShortBlob icon) {
		this.icon = icon;
	}

	public void setPreview(final ShortBlob preview) {
		this.preview = preview;
	}

	public void setState(final PictureStatus state) {
		this.state = state;
	}

	public void setType(final PictureType type) {
		this.type = type;
	}
}
