package com.heys.dating.domain.message;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;
import com.heys.dating.domain.AbstractEntity;
import com.heys.dating.domain.member.Member;

@Entity
@Cache
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MemberMessage extends AbstractEntity {

	static final long serialVersionUID = 2615276985084137787L;
	@Parent
	@Index
	Key<Member> owner;
	Key<Message> message;
	@Index
	Key<Thread> thread;
	Date readTimestamp;
	@Index
	Date sentTimestamp;

	public MemberMessage(final Key<Member> owner, final Key<Thread> thread,
			final Key<Message> message, final Date sentTimestamp) {
		super();
		this.owner = owner;
		this.thread = thread;
		this.message = message;
		this.sentTimestamp = sentTimestamp;
	}
}
