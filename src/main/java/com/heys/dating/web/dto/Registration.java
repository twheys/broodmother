package com.heys.dating.web.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

public class Registration {
	@NotNull
	@Size(min = 3)
	private String login;
	@NotNull
	@Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$")
	@Size(min = 8)
	private String password;
	@NotNull
	@Email
	private String email;
	@NotNull
	private Integer birthday;
	@NotNull
	private Integer birthmonth;
	@NotNull
	private Integer birthyear;

	public Integer getBirthday() {
		return birthday;
	}

	public Integer getBirthmonth() {
		return birthmonth;
	}

	public Integer getBirthyear() {
		return birthyear;
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

	public void setBirthday(final Integer birthday) {
		this.birthday = birthday;
	}

	public void setBirthmonth(final Integer birthmonth) {
		this.birthmonth = birthmonth;
	}

	public void setBirthyear(final Integer birthyear) {
		this.birthyear = birthyear;
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

	@Override
	public String toString() {
		return "Registration [login=" + login + ", password=" + password
				+ ", email=" + email + ", birthday=" + birthday
				+ ", birthmonth=" + birthmonth + ", birthyear=" + birthyear
				+ "]";
	}

}
