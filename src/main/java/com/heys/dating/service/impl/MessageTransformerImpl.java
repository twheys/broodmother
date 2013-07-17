package com.heys.dating.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.appengine.labs.repackaged.com.google.common.collect.Lists;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.heys.dating.domain.member.Member;
import com.heys.dating.domain.message.MemberMessage;
import com.heys.dating.domain.message.MemberThread;
import com.heys.dating.domain.message.Message;
import com.heys.dating.domain.message.Thread;
import com.heys.dating.service.MessageTransformer;
import com.heys.dating.service.dto.message.MessageDTO;
import com.heys.dating.service.dto.message.ParticipantDTO;
import com.heys.dating.service.dto.message.ThreadDTO;
import com.heys.dating.service.dto.message.ThreadListDTO;
import com.heys.dating.service.dto.message.ThreadPreviewDTO;

@Service
public class MessageTransformerImpl implements MessageTransformer {

	@Override
	public ThreadDTO transformThread(final Thread thread,
			final Iterable<MemberMessage> memberMessages,
			final Map<Key<Message>, Message> messageMap,
			final Map<Key<Member>, Member> memberMap,
			final Map<Key<Member>, String> profileMap,
			final Map<Key<Member>, Long> pictureMap) {
		final String subject = thread.getSubject();
		final Date startDate = thread.getStartDate();
		final List<ParticipantDTO> participants = Lists.newArrayList();
		for (final Member member : memberMap.values()) {
			final Key<Member> memberKey = Key.create(member);
			final Long id = member.getId();
			final String login = member.getLogin();
			final String profileVanity = profileMap.get(memberKey);
			final Long pictureId = pictureMap.get(memberKey);

			participants.add(new ParticipantDTO(id, login, profileVanity,
					pictureId));
		}
		final List<MessageDTO> messages = Lists.newArrayList();
		for (final MemberMessage memberMessage : memberMessages) {
			final Message message = messageMap.get(memberMessage.getMessage());
			final Long participantId = memberMap.get(message.getSender())
					.getId();
			final String text = message.getText();
			final Date sentTime = message.getSentTimestamp();
			final Date readTime = message.getReadTimestamp();
			final List<String> pictureUrls = Lists.newArrayList();
			messages.add(new MessageDTO(participantId, text, sentTime,
					readTime, pictureUrls));
		}
		return new ThreadDTO(subject, startDate, participants, messages);
	}

	@Override
	public ThreadListDTO transformThreadList(
			final Iterable<MemberThread> memberThreads,
			final Map<Key<Thread>, Thread> threadMap,
			final Map<Key<Message>, Message> messageMap,
			final Map<Key<Member>, Member> senderMap) {
		final List<ThreadPreviewDTO> threadPreviews = Lists.newArrayList();
		for (final MemberThread memberThread : memberThreads) {
			final ThreadPreviewDTO threadPreviewDto = new ThreadPreviewDTO();
			threadPreviews.add(threadPreviewDto);

			final Thread thread = threadMap.get(memberThread.getThread());
			final Message message = messageMap.get(memberThread
					.getLastMessage());
			final Member sender = senderMap.get(message.getSender());
			final List<String> participants = Lists.newArrayList();
			for (final Ref<Member> participant : thread.getActiveParticipants()) {
				participants.add(participant.get().getLogin());
			}

			threadPreviewDto.setThreadId(thread.getId());
			threadPreviewDto.setSubject(thread.getSubject());
			threadPreviewDto.setLastSender(sender.getLogin());
			threadPreviewDto.setLastMessage(message.getText());
			threadPreviewDto.setUnread(memberThread.isUnread());
			threadPreviewDto.setParticipants(participants);
			threadPreviewDto.setLastUpdated(memberThread.getLastUpdate());
		}
		return new ThreadListDTO(threadPreviews);
	}

}
