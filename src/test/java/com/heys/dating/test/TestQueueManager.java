package com.heys.dating.test;

import lombok.AllArgsConstructor;

import com.googlecode.objectify.Key;
import com.heys.dating.domain.member.Member;
import com.heys.dating.domain.message.Message;
import com.heys.dating.domain.message.Thread;
import com.heys.dating.domain.repository.MemberRepository;
import com.heys.dating.domain.repository.MessageRepository;
import com.heys.dating.domain.repository.ThreadRepository;
import com.heys.dating.manager.MessageManager;
import com.heys.dating.manager.QueueManager;

@AllArgsConstructor
public class TestQueueManager implements QueueManager {

	private final MessageManager messageManager;
	private final MemberRepository memberRepository;
	private final ThreadRepository threadRepository;
	private final MessageRepository messageRepository;

	@Override
	public void deliverMessage(final Key<Member> recipientKey,
			final Key<Thread> threadKey, final Key<Message> messageKey) {
		final Member recipient = memberRepository.findOne(recipientKey);
		final Thread thread = threadRepository.findOne(threadKey);
		final Message message = messageRepository.findOne(messageKey);

		messageManager.sendAsync(recipient, thread, message);
	}
}
