package com.heys.dating.message;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.heys.dating.AbstractEntity;
import com.heys.dating.member.Member;

@Entity
@Cache
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Message extends AbstractEntity {
	private static final long serialVersionUID = 6913633133486456956L;

	Date readTimestamp;
	Key<Member> sender;
	Date sentTimestamp;
	String text;
	Ref<Thread> thread;

	public Message(final Ref<Thread> thread, final Key<Member> sender,
			final String text, final Date sentTimestamp) {
		this.thread = thread;
		this.sender = sender;
		this.text = text;
		this.sentTimestamp = sentTimestamp;
	}
}
