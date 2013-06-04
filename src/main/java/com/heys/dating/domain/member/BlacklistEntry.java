package com.heys.dating.domain.member;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.heys.dating.domain.AbstractVersionedEntity;

@Entity
@Cache
public class BlacklistEntry extends AbstractVersionedEntity {
	private static final long serialVersionUID = -2655677556005711483L;

	@Index
	private String blacklisted;

	@Index
	private String member;

	public BlacklistEntry() {
		super();
	}

	public BlacklistEntry(final String member, final String blacklisted) {
		super();
		this.member = member;
		this.blacklisted = blacklisted;
	}

	public String getBlacklisted() {
		return blacklisted;
	}

	public String getMember() {
		return member;
	}

	public void setBlacklisted(final String blacklisted) {
		this.blacklisted = blacklisted;
	}

	public void setMember(final String member) {
		this.member = member;
	}
}
