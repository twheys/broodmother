package com.heys.dating.domain.user;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.heys.dating.domain.AbstractEntity;

@Entity
public class Customer extends AbstractEntity {
	@Temporal(TemporalType.DATE)
	private Date birthdate;
	private String email;
	private boolean hasExpiredCredentials;
	private boolean isEnabled;
	private boolean isLocked;
	private String login;
	private String password;
	private Set<String> privileges;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Profile profile;

	public Customer(final String login, final String email, final Date birthdate) {
		super();
		this.login = login;
		this.email = email;
		this.birthdate = birthdate;

		password = "";
		isEnabled = true;
		isLocked = false;
		hasExpiredCredentials = false;
		privileges = new HashSet<String>();
		privileges.add("ROLE_USER");
		profile = new Profile();
	}

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

	public Set<String> getPrivileges() {
		return privileges;
	}

	public Profile getProfile() {
		return profile;
	}

	public boolean hasExpiredCredentials() {
		return hasExpiredCredentials;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public boolean isHasExpiredCredentials() {
		return hasExpiredCredentials;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setBirthdate(final Date birthdate) {
		this.birthdate = birthdate;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setEnabled(final boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public void setHasExpiredCredentials(final boolean hasExpiredCredentials) {
		this.hasExpiredCredentials = hasExpiredCredentials;
	}

	public void setLocked(final boolean isLocked) {
		this.isLocked = isLocked;
	}

	public void setLogin(final String login) {
		this.login = login;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public void setPrivileges(final Set<String> privileges) {
		this.privileges = privileges;
	}

	public void setProfile(final Profile profile) {
		this.profile = profile;
	}

	@Override
	public String toString() {
		return "Customer [birthdate=" + birthdate + ", email=" + email
				+ ", hasExpiredCredentials=" + hasExpiredCredentials
				+ ", isEnabled=" + isEnabled + ", isLocked=" + isLocked
				+ ", login=" + login + ", password=" + password
				+ ", privileges=" + privileges + ", profile=" + profile + "]";
	}
}
