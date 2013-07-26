package com.heys.dating.impl.app;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.heys.dating.AbstractEntity;
import com.heys.dating.impl.app.exception.InternalException;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NotFoundException extends InternalException {
	private static final long serialVersionUID = -5466873882955796785L;
	private Class<? extends AbstractEntity> type;

	public NotFoundException(final Class<? extends AbstractEntity> type,
			final String message) {
		super(message);
		this.type = type;
	}

	public NotFoundException(final Class<? extends AbstractEntity> type,
			final String message, final Throwable cause) {
		super(message, cause);
		this.type = type;
	}

	public NotFoundException(final Class<? extends AbstractEntity> type,
			final Throwable cause) {
		super(cause);
		this.type = type;
	}
}
