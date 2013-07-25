package com.heys.dating.message;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.google.appengine.labs.repackaged.com.google.common.collect.Lists;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Load;
import com.heys.dating.AbstractEntity;
import com.heys.dating.member.Member;

@Entity
@Cache
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Thread extends AbstractEntity {
	private static final long serialVersionUID = -2070932576032105785L;

	private String subject;

	@Load
	private List<Ref<Member>> activeParticipants;
	@Load
	private List<Ref<Message>> messages;

	private Date startDate;

	public Thread(final String subject,
			final List<Ref<Member>> activeParticipants) {
		super();
		this.subject = subject;
		this.activeParticipants = activeParticipants;

		startDate = new Date();
		messages = Lists.newArrayList();
	}
}
