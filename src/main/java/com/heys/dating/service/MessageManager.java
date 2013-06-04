package com.heys.dating.service;

import java.util.Collection;
import java.util.List;

import com.googlecode.objectify.Key;
import com.heys.dating.domain.member.Member;
import com.heys.dating.domain.message.Message;
import com.heys.dating.domain.message.MessageThread;
import com.heys.dating.service.impl.MemberNotFoundException;

public interface MessageManager {

	void deliver(String sThreadID, Key<Member> member, Key<Message> message)
			throws MemberNotFoundException;

	Collection<Message> getMessages(String login, String sThreadID,
			Integer count, Integer offset);

	Collection<MessageThread> getThreads(String member, Integer limit,
			Integer offset);

	void send(String from, List<String> to, String text)
			throws MemberNotFoundException;
}
