package com.heys.dating.deeplink;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.heys.dating.impl.app.exception.InternalException;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InvalidDeeplinkException extends InternalException {
	private static final long serialVersionUID = 397291385536272862L;

	public InvalidDeeplinkException(final String message) {
		super(message);
	}

	public InvalidDeeplinkException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public InvalidDeeplinkException(final Throwable cause) {
		super(cause);
	}

}
