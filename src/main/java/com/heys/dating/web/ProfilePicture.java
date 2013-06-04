package com.heys.dating.web;

public class ProfilePicture extends ProfileUpdate {

	private Long pictureId;
	private boolean profilePicture;
	private String gallery;
	private String description;
	private String visibility;

	public String getDescription() {
		return description;
	}

	public String getGallery() {
		return gallery;
	}

	public Long getPictureId() {
		return pictureId;
	}

	public String getVisibility() {
		return visibility;
	}

	public boolean isProfilePicture() {
		return profilePicture;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setGallery(final String gallery) {
		this.gallery = gallery;
	}

	public void setPictureId(final Long pictureId) {
		this.pictureId = pictureId;
	}

	public void setProfilePicture(final boolean profilePicture) {
		this.profilePicture = profilePicture;
	}

	public void setVisibility(final String visibility) {
		this.visibility = visibility;
	}

}
