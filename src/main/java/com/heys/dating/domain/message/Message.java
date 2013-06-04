package com.heys.dating.domain.message;

import java.util.Date;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.heys.dating.domain.AbstractEntity;
import com.heys.dating.domain.member.Member;

@Entity
@Cache
public class Message extends AbstractEntity {
	private static final long serialVersionUID = 7704532339216242856L;

	private Key<Member> sender;
	private String content;
	private Date timestamp;

	public Message() {
		super();
	}

	public Message(final Key<Member> sender, final String content,
			final Date timestamp) {
		super();
		this.sender = sender;
		this.content = content;
		this.timestamp = timestamp;
	}

	public String getContent() {
		return content;
	}

	public Key<Member> getSender() {
		return sender;
	}

	public Date getTimestamp() {
		return timestamp;
	}
}
