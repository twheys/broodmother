package com.heys.dating.web.rest.dto.exception;

public class RestException extends RuntimeException {

	private static final long serialVersionUID = 1407670905547449510L;

	public RestException() {
		super();
	}

	public RestException(final String message) {
		super(message);
	}

	public RestException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public RestException(final Throwable cause) {
		super(cause);
	}

}
