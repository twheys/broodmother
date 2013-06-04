package com.heys.dating.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerException extends WebException {

	private static final long serialVersionUID = -3848580611256534527L;

	public InternalServerException() {
		super();
	}

	public InternalServerException(final String message) {
		super(message);
	}

	public InternalServerException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public InternalServerException(final Throwable cause) {
		super(cause);
	}

}
