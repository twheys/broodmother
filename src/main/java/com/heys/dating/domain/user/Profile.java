package com.heys.dating.domain.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import com.heys.dating.domain.AbstractEntity;

@Entity
public class Profile extends AbstractEntity {
	private Customer customer;
	private String description;
	private String headline;
	private List<Gallery> galleries;

	public Profile() {
		setGalleries(new ArrayList<Gallery>());
	}

	public Customer getCustomer() {
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

	public void setCustomer(final Customer customer) {
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

	@Override
	public String toString() {
		return "Profile [customer=" + customer + ", description=" + description
				+ ", headline=" + headline + ", galleries=" + galleries + "]";
	}
}
