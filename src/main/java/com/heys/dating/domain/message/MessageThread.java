package com.heys.dating.domain.message;

import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Parent;
import com.heys.dating.domain.AbstractEntity;
import com.heys.dating.domain.member.Member;

@Entity
@Cache
public class MessageThread extends AbstractEntity {
	private static final long serialVersionUID = -2070932576032105785L;

	@Parent
	private Key<Member> owner;
	@Load
	private List<Key<Message>> messages;
	private String sharedID;
	private boolean read;
	private boolean deleted;
	private Date lastUpdate;

	public MessageThread() {
		super();
	}

	public MessageThread(final String sharedID, final Key<Member> owner) {
		this.sharedID = sharedID;
		this.owner = owner;

		read = false;
		deleted = false;
		messages = Lists.newArrayList();
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public List<Key<Message>> getMessages() {
		return messages;
	}

	public Key<Member> getOwner() {
		return owner;
	}

	public String getSharedID() {
		return sharedID;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public boolean isRead() {
		return read;
	}

	public void setDeleted(final boolean deleted) {
		this.deleted = deleted;
	}

	public void setLastUpdate(final Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public void setRead(final boolean read) {
		this.read = read;
	}
}
