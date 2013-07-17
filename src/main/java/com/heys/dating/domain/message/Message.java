package com.heys.dating.domain.message;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Parent;
import com.heys.dating.domain.AbstractEntity;
import com.heys.dating.domain.member.Member;

@Entity
@Cache
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Message extends AbstractEntity {
	private static final long serialVersionUID = 6913633133486456956L;

	@Parent
	@Getter
	Ref<Thread> thread;
	@Getter
	Key<Member> sender;
	@Getter
	String text;
	@Getter
	Date sentTimestamp;
	@Getter
	@Setter
	Date readTimestamp;

	public Message(final Ref<Thread> thread, final Key<Member> sender,
			final String text, final Date sentTimestamp) {
		super();
		this.thread = thread;
		this.sender = sender;
		this.text = text;
		this.sentTimestamp = sentTimestamp;
	}
}
