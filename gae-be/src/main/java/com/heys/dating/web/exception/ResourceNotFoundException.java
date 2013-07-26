package com.heys.dating.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends WebException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1982561229086363894L;

}
