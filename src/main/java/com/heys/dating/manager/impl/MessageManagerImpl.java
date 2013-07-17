package com.heys.dating.manager.impl;

import static com.heys.dating.util.DatastoreUtil.refs;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.appengine.labs.repackaged.com.google.common.collect.Lists;
import com.google.appengine.labs.repackaged.com.google.common.collect.Maps;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.heys.dating.domain.member.Member;
import com.heys.dating.domain.member.Picture;
import com.heys.dating.domain.member.Profile;
import com.heys.dating.domain.message.MemberMessage;
import com.heys.dating.domain.message.MemberThread;
import com.heys.dating.domain.message.Message;
import com.heys.dating.domain.message.Thread;
import com.heys.dating.domain.repository.MemberMessageRepository;
import com.heys.dating.domain.repository.MemberRepository;
import com.heys.dating.domain.repository.MemberThreadRepository;
import com.heys.dating.domain.repository.MessageRepository;
import com.heys.dating.domain.repository.ProfileRepository;
import com.heys.dating.domain.repository.ThreadRepository;
import com.heys.dating.manager.BlacklistManager;
import com.heys.dating.manager.MessageManager;
import com.heys.dating.manager.QueueManager;
import com.heys.dating.service.MessageTransformer;
import com.heys.dating.service.dto.message.ThreadDTO;
import com.heys.dating.service.dto.message.ThreadListDTO;

@Service
public class MessageManagerImpl implements MessageManager {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private ThreadRepository threadRepository;

	@Autowired
	private MemberThreadRepository memberThreadRepository;

	@Autowired
	private MemberMessageRepository memberMessageRepository;

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private BlacklistManager blacklistManager;

	@Autowired
	private MessageTransformer messageTransformer;

	@Autowired
	private QueueManager queueManager;

	private MemberMessage createMemberMessage(final Member recipient,
			final Message message, final Thread thread) {
		final MemberMessage memberMessage = new MemberMessage(
				Key.create(recipient), Key.create(thread), Key.create(message),
				message.getSentTimestamp());
		return memberMessageRepository.save(memberMessage);
	}

	private MemberThread createMemberThread(final Member owner,
			final Thread thread) {
		return memberThreadRepository.save(new MemberThread(Key.create(owner),
				Key.create(thread)));
	}

	private Message createMessage(final Member sender, final Thread thread,
			final String text) {
		final Message message = new Message(Ref.create(thread),
				Key.create(sender), text, new Date());
		return messageRepository.save(message);
	}

	private MemberThread createOrGetMemberThread(final Member owner,
			final Thread thread) {
		final MemberThread memberThread = memberThreadRepository
				.findByOwnerAndThread(owner, thread);
		if (null == memberThread)
			return createMemberThread(owner, thread);
		return memberThread;
	}

	private Thread createThread(final String subject,
			final List<Member> participants) {
		final List<Ref<Member>> paricipantRefs = Lists
				.newArrayList(refs(participants));
		return threadRepository.save(new Thread(subject, paricipantRefs));
	}

	@Override
	public ThreadDTO getMessagesForThread(final Member member,
			final Thread thread, final Integer limit, final Integer offset) {
		final Iterable<MemberMessage> memberMessages = memberMessageRepository
				.findByOwnerAndThread(member, thread, limit, offset,
						"-sentTimestamp");

		final List<Key<Message>> messageKeys = Lists.newArrayList();
		for (final MemberMessage memberMessage : memberMessages) {
			messageKeys.add(memberMessage.getMessage());
		}

		final Map<Key<Message>, Message> messageMap = messageRepository
				.findAll(messageKeys);
		final Map<Key<Member>, Member> memberMap = Maps.newHashMap();
		final List<Key<Profile>> profileKeys = Lists.newArrayList();
		for (final Ref<Member> memberRef : thread.getActiveParticipants()) {
			final Member participant = memberRef.get();
			final Key<Member> participantKey = Key.create(participant);
			memberMap.put(participantKey, participant);
			profileKeys.add(participant.getProfile().getKey());
		}

		final Map<Key<Profile>, Profile> profiles = profileRepository
				.findAll(profileKeys);
		final Map<Key<Member>, String> profileMap = Maps.newHashMap();
		final Map<Key<Member>, Long> pictureMap = Maps.newHashMap();
		for (final Profile profile : profiles.values()) {
			profileMap.put(profile.getOwner(), profile.getVanity());
			final Ref<Picture> pictureRef = profile.getProfilePicture();
			if (null == pictureRef) {
				pictureMap.put(profile.getOwner(), null);
			} else {
				pictureMap.put(profile.getOwner(), pictureRef.get().getId());
			}
		}
		return messageTransformer.transformThread(thread, memberMessages,
				messageMap, memberMap, profileMap, pictureMap);
	}

	@Override
	public ThreadListDTO getNewThreadsForMember(final Member member,
			final Integer limit, final Integer offset) {
		final Iterable<MemberThread> memberThreads = memberThreadRepository
				.findByOwner(member, limit, offset, "-lastUpdate");
		final List<Key<Thread>> threadKeys = Lists.newArrayList();
		final List<Key<Message>> messageKeys = Lists.newArrayList();
		for (final MemberThread memberThread : memberThreads) {
			threadKeys.add(memberThread.getThread());
			messageKeys.add(memberThread.getLastMessage());
		}

		final Map<Key<Thread>, Thread> threadMap = threadRepository
				.findAll(threadKeys);
		final Map<Key<Message>, Message> messageMap = messageRepository
				.findAll(messageKeys);

		final List<Key<Member>> senderKeys = Lists.newArrayList();
		for (final Message message : messageMap.values()) {
			senderKeys.add(message.getSender());
		}
		final Map<Key<Member>, Member> senderMap = memberRepository
				.findAll(senderKeys);

		return messageTransformer.transformThreadList(memberThreads, threadMap,
				messageMap, senderMap);
	}

	@Override
	public Message send(final Member sender, final List<Member> recipients,
			final String text) {
		return send(sender, recipients, null, text);
	}

	@Override
	public Message send(final Member sender, final List<Member> recipients,
			final String subject, final String text) {
		final List<Member> participants = Lists.newArrayList(sender);
		participants.addAll(recipients);
		final Thread thread = createThread(subject, participants);
		return send(sender, thread, text);
	}

	@Override
	public Message send(final Member sender, final Member recipient,
			final String text) {
		return send(sender, recipient, null, text);
	}

	@Override
	public Message send(final Member sender, final Member recipient,
			final String subject, final String text) {
		return send(sender, Lists.newArrayList(recipient), subject, text);
	}

	@Override
	public Message send(final Member sender, final Thread thread,
			final String text) {
		final Message message = createMessage(sender, thread, text);
		thread.getMessages().add(Ref.create(message));
		threadRepository.save(thread);

		for (final Ref<Member> participant : thread.getActiveParticipants()) {
			queueManager.deliverMessage(participant.getKey(),
					Key.create(thread), Key.create(message));
			// sendAsync(participant.get(), thread, message);
		}
		return message;
	}

	@Override
	public void sendAsync(final Member recipient, final Thread thread,
			final Message message) {
		final MemberThread memberThread = createOrGetMemberThread(recipient,
				thread);
		final MemberMessage memberMessage = createMemberMessage(recipient,
				message, thread);

		memberThread.setLastMessage(Key.create(message));
		memberThread.setLastUpdate(new Date());
		memberThread.setUnread(true);
		memberThread.getMessages().add(Ref.create(memberMessage));
		memberThreadRepository.save(memberThread);
	}
}
