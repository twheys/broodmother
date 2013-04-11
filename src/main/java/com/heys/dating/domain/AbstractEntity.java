package com.heys.dating.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import com.google.appengine.api.datastore.Key;

@Entity
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;

	private Date creationDate;
	private Date modificationDate;
	private long version;

	public AbstractEntity() {
		creationDate = new Date();
		modificationDate = new Date();
		version = 1L;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public Key getKey() {
		return key;
	}

	public Date getModificationDate() {
		return modificationDate;
	}

	public long getVersion() {
		return version;
	}

	public void setCreationDate(final Date creationDate) {
		this.creationDate = creationDate;
	}

	public void setKey(final Key key) {
		this.key = key;
	}

	public void setModificationDate(final Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	public void setVersion(final long version) {
		this.version = version;
	}
}
