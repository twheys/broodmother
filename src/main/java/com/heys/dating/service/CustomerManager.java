package com.heys.dating.service;

import java.util.Date;

import com.heys.dating.domain.user.Customer;

public interface CustomerManager {
	Customer register(String login, String password, String email);

	Customer register(String login, String password, String email,
			Date birthdate);
}
