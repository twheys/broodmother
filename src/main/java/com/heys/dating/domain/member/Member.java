package com.heys.dating.domain.member;

import java.util.Date;
import java.util.Set;

import com.google.common.collect.Sets;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.OnSave;
import com.heys.dating.domain.AbstractVersionedEntity;

@Entity
@Cache
public class Member extends AbstractVersionedEntity {
	private static final long serialVersionUID = 8438508214003815930L;

	private String login;
	private String email;
	@Index
	private String loginIgnoreCase;
	@Index
	private String emailIgnoreCase;
	private String password;
	private String locale;
	private boolean hasExpiredCredentials;
	private boolean isActivated;
	private boolean isBanned;
	private boolean isEnabled;
	private boolean isLocked;
	private Date birthdate;

	private Set<MemberRole> privileges;
	private Key<Profile> profile;

	Member() {
		super();
	}

	public Member(final String login, final String email, final Date birthdate,
			final String locale) {
		super();
		this.login = login;
		this.email = email;
		this.birthdate = birthdate;
		this.locale = locale;

		password = "";

		hasExpiredCredentials = false;
		isActivated = false;
		isBanned = false;
		isEnabled = true;
		isLocked = false;

		privileges = Sets.newHashSet(MemberRole.ROLE_USER);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Member other = (Member) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		return true;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public String getEmail() {
		return email;
	}

	public String getEmailIgnoreCase() {
		return emailIgnoreCase;
	}

	public String getLocale() {
		return locale;
	}

	public String getLogin() {
		return login;
	}

	public String getLoginIgnoreCase() {
		return loginIgnoreCase;
	}

	public String getPassword() {
		return password;
	}

	public Set<MemberRole> getPrivileges() {
		return privileges;
	}

	public Key<Profile> getProfile() {
		return profile;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (email == null ? 0 : email.hashCode());
		result = prime * result + (login == null ? 0 : login.hashCode());
		return result;
	}

	public boolean isActivated() {
		return isActivated;
	}

	public boolean isBanned() {
		return isBanned;
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

	@Override
	@OnSave
	public void onSave() {
		super.onSave();
		if (null != login) {
			loginIgnoreCase = login.toUpperCase();
		}
		if (null != email) {
			emailIgnoreCase = email.toUpperCase();
		}

	}

	public void setActivated(final boolean isActivated) {
		this.isActivated = isActivated;
	}

	public void setBanned(final boolean isBanned) {
		this.isBanned = isBanned;
	}

	public void setBirthdate(final Date birthdate) {
		this.birthdate = birthdate;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setEmailIgnoreCase(final String emailIgnoreCase) {
		this.emailIgnoreCase = emailIgnoreCase;
	}

	public void setEnabled(final boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public void setHasExpiredCredentials(final boolean hasExpiredCredentials) {
		this.hasExpiredCredentials = hasExpiredCredentials;
	}

	public void setLocale(final String locale) {
		this.locale = locale;
	}

	public void setLocked(final boolean isLocked) {
		this.isLocked = isLocked;
	}

	public void setLogin(final String login) {
		this.login = login;
	}

	public void setLoginIgnoreCase(final String loginIgnoreCase) {
		this.loginIgnoreCase = loginIgnoreCase;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public void setPrivileges(final Set<MemberRole> privileges) {
		this.privileges = privileges;
	}

	public void setProfile(final Key<Profile> profile) {
		this.profile = profile;
	}

	@Override
	public String toString() {
		return "Member [login=" + login + ", email=" + email
				+ ", password=<PROTECTED>" + ", locale=" + locale
				+ ", hasExpiredCredentials=" + hasExpiredCredentials
				+ ", isActivated=" + isActivated + ", isBanned=" + isBanned
				+ ", isEnabled=" + isEnabled + ", isLocked=" + isLocked
				+ ", birthdate=" + birthdate + ", privileges=" + privileges
				+ "]";
	}

}
