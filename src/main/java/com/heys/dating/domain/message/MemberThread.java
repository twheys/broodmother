package com.heys.dating.domain.message;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.google.appengine.labs.repackaged.com.google.common.collect.Lists;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.condition.PojoIf;
import com.heys.dating.domain.AbstractEntity;
import com.heys.dating.domain.member.Member;

@Entity
@Cache
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MemberThread extends AbstractEntity {
	static class IfNotDeleted extends PojoIf<MemberThread> {
		@Override
		public boolean matchesPojo(final MemberThread pojo) {
			return pojo.deleteDate == null;
		}
	}

	private static final long serialVersionUID = -4131943578081886824L;

	@Index(IfNotDeleted.class)
	Key<Thread> thread;
	@Parent
	@Index(IfNotDeleted.class)
	Key<Member> owner;
	Key<Message> lastMessage;
	@Load
	final List<Ref<MemberMessage>> messages = Lists.newArrayList();
	@Index(IfNotDeleted.class)
	Date lastUpdate;
	Date deleteDate;
	boolean unread;

	public MemberThread(final Key<Member> owner, final Key<Thread> thread) {
		this.owner = owner;
		this.thread = thread;
	}
}
