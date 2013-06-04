package com.heys.dating.domain;

import java.util.Date;

import com.googlecode.objectify.annotation.EntitySubclass;
import com.googlecode.objectify.annotation.OnSave;

@EntitySubclass
public abstract class AbstractVersionedEntity extends AbstractEntity {
	private static final long serialVersionUID = 4150320948527035578L;

	private Date creationDate;
	private Date modificationDate;
	private long version;

	public AbstractVersionedEntity() {
		super();
		creationDate = new Date();
		version = 0L;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public Date getModificationDate() {
		return modificationDate;
	}

	public long getVersion() {
		return version;
	}

	@OnSave
	public void onSave() {
		modificationDate = new Date();
		++version;
	}
}
