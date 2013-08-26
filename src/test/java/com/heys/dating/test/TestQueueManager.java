package com.heys.dating.test;

import lombok.NoArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;

import com.googlecode.objectify.Key;
import com.heys.dating.member.Member;
import com.heys.dating.member.MemberRepository;
import com.heys.dating.message.Message;
import com.heys.dating.message.MessageRepository;
import com.heys.dating.message.MessageService;
import com.heys.dating.message.Thread;
import com.heys.dating.message.ThreadRepository;
import com.heys.dating.service.QueueManager;

@NoArgsConstructor
public class TestQueueManager implements QueueManager {

	@Autowired
	private MessageService messageService;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private ThreadRepository threadRepository;

	@Autowired
	private MessageRepository messageRepository;

	@Override
	public void deliverMessage(final Key<Member> recipientKey,
			final Key<Thread> threadKey, final Key<Message> messageKey) {
		final Member recipient = memberRepository.findOne(recipientKey);
		final Thread thread = threadRepository.findOne(threadKey);
		final Message message = messageRepository.findOne(messageKey);

		messageService.sendAsync(recipient, thread, message);
	}
}
