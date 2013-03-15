package com.heys.dating.domain.user;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.heys.dating.domain.AbstractEntity;

@Entity
public class Customer extends AbstractEntity {
	private String login;
	@Temporal(TemporalType.DATE)
	private Date birthdate;
	private String email;
	@JoinColumn
	private Profile profile;
	private String password;

	public Date getBirthdate() {
		return birthdate;
	}

	public String getEmail() {
		return email;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public void setBirthdate(final Date birthdate) {
		this.birthdate = birthdate;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setLogin(final String login) {
		this.login = login;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
}
