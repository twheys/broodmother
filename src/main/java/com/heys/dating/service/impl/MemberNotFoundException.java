package com.heys.dating.service.impl;

import java.util.Collection;

import com.google.common.collect.Lists;

public class MemberNotFoundException extends Exception {
	private static final long serialVersionUID = 8300445647975354563L;

	private final Collection<String> missing;

	public MemberNotFoundException(final Collection<String> missing) {
		this.missing = missing;
	}

	public MemberNotFoundException(final String missing) {
		this(Lists.newArrayList(missing));
	}

	public Collection<String> getMissing() {
		return missing;
	}
}
