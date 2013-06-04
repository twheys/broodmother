package com.heys.dating.service.impl;

public class NotFoundException extends Exception {
	private static final long serialVersionUID = -5466873882955796785L;

	private Object target;

	public NotFoundException() {
		super();
	}

	public NotFoundException(final Object target) {
		super();
		this.target = target;
	}

	public Object getTarget() {
		return target;
	}

	public void setTarget(final Object target) {
		this.target = target;
	}

}
