package com.heys.dating.web;

import java.util.List;

public class ProfileDetails extends ProfileUpdate {
	private String gender;
	private String zipCode;
	private String status;
	private List<String> partnerGenders;
	private Integer partnerAgeMin;
	private Integer partnerAgeMax;

	public String getGender() {
		return gender;
	}

	public Integer getPartnerAgeMax() {
		return partnerAgeMax;
	}

	public Integer getPartnerAgeMin() {
		return partnerAgeMin;
	}

	public List<String> getPartnerGenders() {
		return partnerGenders;
	}

	public String getStatus() {
		return status;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setGender(final String gender) {
		this.gender = gender;
	}

	public void setPartnerAgeMax(final Integer partnerAgeMax) {
		this.partnerAgeMax = partnerAgeMax;
	}

	public void setPartnerAgeMin(final Integer partnerAgeMin) {
		this.partnerAgeMin = partnerAgeMin;
	}

	public void setPartnerGenders(final List<String> partnerGenders) {
		this.partnerGenders = partnerGenders;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public void setZipCode(final String zipCode) {
		this.zipCode = zipCode;
	}

}
