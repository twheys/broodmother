package com.heys.dating.service.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.googlecode.objectify.Key;
import com.heys.dating.domain.member.Member;
import com.heys.dating.domain.message.Message;
import com.heys.dating.domain.message.MessageThread;
import com.heys.dating.domain.repository.MemberRepository;
import com.heys.dating.domain.repository.MessageRepository;
import com.heys.dating.domain.repository.MessageThreadRepository;
import com.heys.dating.service.BlacklistManager;
import com.heys.dating.service.MessageManager;
import com.heys.dating.service.QueueManager;

@Service
public class MessageManagerImpl implements MessageManager {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private BlacklistManager blacklistService;

	@Autowired
	private QueueManager queueManager;

	@Autowired
	private MessageThreadRepository messageThreadRepository;

	private Message createMessage(final String text, final Member sender) {
		final Message message = new Message(Key.create(sender), text,
				new Date());
		messageRepository.save(message);
		return message;
	}

	private String createSharedThreadID(final String from, final List<String> to) {
		final List<String> members = Lists.newArrayList(from);
		members.addAll(to);
		Collections.sort(members);
		return Joiner.on(':').join(members);
	}

	@Override
	public void deliver(final String sThreadID, final Key<Member> member,
			final Key<Message> message) throws MemberNotFoundException {
		final MessageThread thread = getThread(sThreadID, member);
		thread.getMessages().add(message);
		thread.setLastUpdate(new Date());
		thread.setRead(false);
		messageThreadRepository.save(thread);
	}

	@Override
	public Collection<Message> getMessages(final String login,
			final String sThreadID, final Integer count, final Integer offset) {
		final Member member = memberRepository.findByLogin(login);
		final MessageThread thread = messageThreadRepository
				.findByMemberAndSharedID(Key.create(member), sThreadID);
		return messageRepository.findAll(thread.getMessages().subList(offset,
				count));
	}

	private MessageThread getThread(final String sThreadID,
			final Key<Member> member) {
		final MessageThread thread = messageThreadRepository
				.findByMemberAndSharedID(member, sThreadID);
		if (null != thread)
			return thread;

		return new MessageThread(sThreadID, member);
	}

	@Override
	public Collection<MessageThread> getThreads(final String login,
			final Integer limit, final Integer offset) {
		final Member member = memberRepository.findByLogin(login);
		return messageThreadRepository.findNewForMember(Key.create(member),
				limit, offset);
	}

	/**
	 * Calls {@link #deliver(Member, Message)}
	 * 
	 * @param to
	 * @param sender
	 * @param message
	 * @param stuid
	 */
	private void queueDelivery(final String sThreadID, final List<String> to,
			final Member sender, final Message message) {
		final Key<Message> messageKey = Key.create(message);
		queueManager.deliverMessage(sThreadID, Key.create(sender), messageKey);
		final List<Member> recipients = memberRepository.findByLogin(to);
		for (final Member recipient : recipients) {
			queueManager.deliverMessage(sThreadID, Key.create(recipient),
					messageKey);
		}
	}

	@Override
	public void send(final String from, final List<String> to, final String text)
			throws MemberNotFoundException {
		final Member sender = memberRepository.findByLogin(from);
		final Message message = createMessage(text, sender);
		final String sThreadID = createSharedThreadID(from, to);
		queueDelivery(sThreadID, to, sender, message);
	}
}
