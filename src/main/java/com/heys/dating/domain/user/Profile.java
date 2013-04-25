package com.heys.dating.domain.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.heys.dating.domain.AbstractEntity;

@Entity
public class Profile extends AbstractEntity {
	private static final long serialVersionUID = 3370733434142254463L;

	private Member customer;
	private String description;
	private String headline;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Picture profilePicture;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Gallery> galleries;

	public Profile() {
		setGalleries(new ArrayList<Gallery>());
	}

	public Member getCustomer() {
		return customer;
	}

	public String getDescription() {
		return description;
	}

	public List<Gallery> getGalleries() {
		return galleries;
	}

	public String getHeadline() {
		return headline;
	}

	public Picture getProfilePicture() {
		return profilePicture;
	}

	public void setCustomer(final Member customer) {
		this.customer = customer;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setGalleries(final List<Gallery> galleries) {
		this.galleries = galleries;
	}

	public void setHeadline(final String headline) {
		this.headline = headline;
	}

	public void setProfilePicture(final Picture profilePicture) {
		this.profilePicture = profilePicture;
	}

	@Override
	public String toString() {
		return "Profile [customer=" + customer + ", description=" + description
				+ ", headline=" + headline + ", galleries=" + galleries + "]";
	}
}
