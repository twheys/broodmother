package com.heys.dating.domain.user;

import javax.persistence.Basic;
import javax.persistence.Entity;

import com.google.appengine.api.datastore.ShortBlob;
import com.heys.dating.domain.AbstractEntity;

@Entity
public class Picture extends AbstractEntity {
	private static final long serialVersionUID = 8418671229417665202L;

	@Basic
	private ShortBlob full;

	@Basic
	private ShortBlob preview;

	@Basic
	private ShortBlob icon;

	private String description;
	private PictureType type;
	private FileType fileType;
	private PictureState state;

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

	public PictureState getState() {
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

	public void setState(final PictureState state) {
		this.state = state;
	}

	public void setType(final PictureType type) {
		this.type = type;
	}

}
