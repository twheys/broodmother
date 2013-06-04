package com.heys.dating.domain;

import java.io.Serializable;

import com.googlecode.objectify.annotation.EntitySubclass;
import com.googlecode.objectify.annotation.Id;

@EntitySubclass
public abstract class AbstractEntity implements Serializable {
	private static final long serialVersionUID = -8729928336779606038L;

	@Id
	private Long id;

	public AbstractEntity() {
		// Default constructor
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final AbstractEntity other = (AbstractEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (id == null ? 0 : id.hashCode());
		return result;
	}

}
