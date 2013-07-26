package com.heys.dating.message;

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
import com.heys.dating.AbstractEntity;
import com.heys.dating.member.Member;

@Entity
@Cache
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ThreadLeaf extends AbstractEntity {
	protected static class IfNotDeleted extends PojoIf<ThreadLeaf> {
		@Override
		public boolean matchesPojo(final ThreadLeaf pojo) {
			return pojo.deleteDate == null;
		}
	}

	private static final long serialVersionUID = -4131943578081886824L;

	Date deleteDate;
	Key<Message> lastMessage;
	@Index(IfNotDeleted.class)
	Date lastUpdate;
	@Load
	final List<Ref<MessageLeaf>> messages = Lists.newArrayList();
	@Parent
	@Index(IfNotDeleted.class)
	Key<Member> owner;
	@Index(IfNotDeleted.class)
	Key<Thread> thread;
	boolean unread;

	public ThreadLeaf(final Key<Member> owner, final Key<Thread> thread) {
		this.owner = owner;
		this.thread = thread;
	}
}
