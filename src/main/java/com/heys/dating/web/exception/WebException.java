package com.heys.dating.web.exception;

public class WebException extends RuntimeException {

	private static final long serialVersionUID = 1407670905547449510L;

	public WebException() {
		super();
	}

	public WebException(final String message) {
		super(message);
	}

	public WebException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public WebException(final Throwable cause) {
		super(cause);
	}

}
