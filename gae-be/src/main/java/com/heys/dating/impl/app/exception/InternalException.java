package com.heys.dating.impl.app.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InternalException extends Exception {
	private static final long serialVersionUID = 3313726560941594694L;

	public InternalException(final String message) {
		super(message);
	}

	public InternalException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public InternalException(final Throwable cause) {
		super(cause);
	}

}
