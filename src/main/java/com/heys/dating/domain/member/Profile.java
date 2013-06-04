package com.heys.dating.domain.member;

import java.util.List;

import com.google.common.collect.Lists;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;
import com.heys.dating.domain.AbstractVersionedEntity;

@Entity
@Cache
public class Profile extends AbstractVersionedEntity {
	private static final long serialVersionUID = 3370733434142254463L;

	@Parent
	private Key<Member> owner;
	@Index
	private String vanity;
	private String description;
	private RelationshipStatus status;
	private Gender gender;
	private List<Gender> partnerGenders;
	private Integer partnerAgeMin;
	private Integer partnerAgeMax;
	private boolean isOnline;
	private Key<Picture> profilePicture;

	private List<Ref<Gallery>> galleries;

	private ProfileCompletion profileCompletion;

	public Profile() {
		super();
	}

	public Profile(final String vanity) {
		super();
		this.vanity = vanity;

		galleries = Lists.newArrayList();
	}

	public String getDescription() {
		return description;
	}

	public List<Ref<Gallery>> getGalleries() {
		return galleries;
	}

	public Gender getGender() {
		return gender;
	}

	public Key<Member> getOwner() {
		return owner;
	}

	public Integer getPartnerAgeMax() {
		return partnerAgeMax;
	}

	public Integer getPartnerAgeMin() {
		return partnerAgeMin;
	}

	public List<Gender> getPartnerGenders() {
		return partnerGenders;
	}

	public ProfileCompletion getProfileCompletion() {
		return profileCompletion;
	}

	public Key<Picture> getProfilePicture() {
		return profilePicture;
	}

	public RelationshipStatus getStatus() {
		return status;
	}

	public String getVanity() {
		return vanity;
	}

	public boolean isOnline() {
		return isOnline;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setGalleries(final List<Ref<Gallery>> galleries) {
		this.galleries = galleries;
	}

	public void setGender(final Gender gender) {
		this.gender = gender;
	}

	public void setOnline(final boolean isOnline) {
		this.isOnline = isOnline;
	}

	public void setOwner(final Key<Member> owner) {
		this.owner = owner;
	}

	public void setPartnerAgeMax(final Integer partnerAgeMax) {
		this.partnerAgeMax = partnerAgeMax;
	}

	public void setPartnerAgeMin(final Integer partnerAgeMin) {
		this.partnerAgeMin = partnerAgeMin;
	}

	public void setPartnerGenders(final List<Gender> partnerGenders) {
		this.partnerGenders = partnerGenders;
	}

	public void setProfileCompletion(final ProfileCompletion profileCompletion) {
		this.profileCompletion = profileCompletion;
	}

	public void setProfilePicture(final Key<Picture> profilePicture) {
		this.profilePicture = profilePicture;
	}

	public void setStatus(final RelationshipStatus status) {
		this.status = status;
	}

	public void setVanity(final String vanity) {
		this.vanity = vanity;
	}
}
