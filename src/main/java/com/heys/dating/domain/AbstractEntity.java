package com.heys.dating.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import com.googlecode.objectify.annotation.EntitySubclass;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.OnSave;

@EntitySubclass
@EqualsAndHashCode(of = "id", callSuper = false)
public abstract class AbstractEntity implements Serializable {
	private static final long serialVersionUID = -8729928336779606038L;

	@Id
	@Getter
	private Long id;

	@Getter
	private Date creationDate;

	@Getter
	private Date modificationDate;

	@Getter
	private long version;

	public AbstractEntity() {
		creationDate = new Date();
		version = 0L;
	}

	@OnSave
	public void onSave() {
		modificationDate = new Date();
		++version;
	}
}
