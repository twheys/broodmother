package com.heys.dating.message;

import static com.heys.dating.util.DatastoreUtil.c;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.OnSave;
import com.heys.dating.AbstractEntity;

@Entity
@Cache
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BlacklistEntry extends AbstractEntity {
	private static final long serialVersionUID = -2655677556005711483L;

	@Index
	private String member;

	@Index
	private String target;

	public BlacklistEntry(final String member, final String target) {
		super();
		this.member = member;
		this.target = target;
	}

	@Override
	@OnSave
	public void onSave() {
		member = c(member);
		target = c(target);
	}
}
