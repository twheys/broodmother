package com.heys.dating.web.html.dto;


public class RegistrationForm {
	private String login;
	private String password;
	private String email;
	private String birthdateDD;
	private String birthdateMM;
	private String birthdateYYYY;

	public String getBirthdateDD() {
		return birthdateDD;
	}

	public String getBirthdateMM() {
		return birthdateMM;
	}

	public String getBirthdateYYYY() {
		return birthdateYYYY;
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

	public void setBirthdateDD(final String birthdateDD) {
		this.birthdateDD = birthdateDD;
	}

	public void setBirthdateMM(final String birthdateMM) {
		this.birthdateMM = birthdateMM;
	}

	public void setBirthdateYYYY(final String birthdateYYYY) {
		this.birthdateYYYY = birthdateYYYY;
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

}
