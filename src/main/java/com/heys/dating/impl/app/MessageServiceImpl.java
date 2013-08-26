package com.heys.dating.impl.app;

import static com.heys.dating.util.DatastoreUtil.refs;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.appengine.labs.repackaged.com.google.common.collect.Lists;
import com.google.appengine.labs.repackaged.com.google.common.collect.Maps;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.heys.dating.member.Member;
import com.heys.dating.member.MemberRepository;
import com.heys.dating.message.Message;
import com.heys.dating.message.MessageLeaf;
import com.heys.dating.message.MessageLeafRepository;
import com.heys.dating.message.MessageRepository;
import com.heys.dating.message.MessageService;
import com.heys.dating.message.MessageTransformer;
import com.heys.dating.message.Thread;
import com.heys.dating.message.ThreadLeaf;
import com.heys.dating.message.ThreadLeafRepository;
import com.heys.dating.message.ThreadRepository;
import com.heys.dating.message.dto.ThreadDTO;
import com.heys.dating.message.dto.ThreadListDTO;
import com.heys.dating.picture.Picture;
import com.heys.dating.profile.Profile;
import com.heys.dating.profile.ProfileRepository;
import com.heys.dating.service.QueueManager;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private MessageLeafRepository messageLeafRepository;

	@Autowired
	private MessageRepository messageMessageRepository;

	@Autowired
	private MessageTransformer messageTransformer;

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private QueueManager queueManager;

	@Autowired
	private ThreadLeafRepository threadLeafRepository;

	@Autowired
	private ThreadRepository threadRepository;

	private MessageLeaf createMessageLeaf(final Member recipient,
			final Message message, final Thread thread) {
		final MessageLeaf memberMessage = new MessageLeaf(
				Key.create(recipient), Key.create(thread), Key.create(message),
				message.getSentTimestamp());
		if (message.getSender().equals(Key.create(recipient))) {
			memberMessage.setReadTimestamp(new Date());
		}
		return messageLeafRepository.validateAndSave(memberMessage);
	}

	private ThreadLeaf createOrGetThreadLeaf(final Member owner,
			final Thread thread) {
		final ThreadLeaf memberThread = threadLeafRepository
				.findByOwnerAndThread(owner, thread);
		if (null == memberThread)
			return createThreadLeaf(owner, thread);
		return memberThread;
	}

	private Message createTextMessage(final Member sender, final Thread thread,
			final String text) {
		final Message message = new Message(Ref.create(thread),
				Key.create(sender), text, new Date());
		return messageMessageRepository.validateAndSave(message);
	}

	private Thread createThread(final String subject,
			final List<Member> participants) {
		final List<Ref<Member>> paricipantRefs = Lists
				.newArrayList(refs(participants));
		return threadRepository.validateAndSave(new Thread(subject,
				paricipantRefs));
	}

	private ThreadLeaf createThreadLeaf(final Member owner, final Thread thread) {
		return threadLeafRepository.validateAndSave(new ThreadLeaf(Key
				.create(owner), Key.create(thread)));
	}

	@Override
	public ThreadDTO getMessagesForThread(final Member member,
			final Thread thread, final Integer limit, final Integer offset) {
		final Iterable<MessageLeaf> memberMessages = messageLeafRepository
				.findByOwnerAndThread(member, thread, limit, offset,
						"-sentTimestamp");

		final List<Key<Message>> messageKeys = Lists.newArrayList();
		for (final MessageLeaf memberMessage : memberMessages) {
			messageKeys.add(memberMessage.getMessage());
		}

		final Map<Key<Message>, Message> messageMap = messageMessageRepository
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
		final Iterable<ThreadLeaf> memberThreads = threadLeafRepository
				.findByOwner(member, limit, offset, "-lastUpdate");
		final List<Key<Thread>> threadKeys = Lists.newArrayList();
		final List<Key<Message>> messageKeys = Lists.newArrayList();
		for (final ThreadLeaf memberThread : memberThreads) {
			threadKeys.add(memberThread.getThread());
			messageKeys.add(memberThread.getLastMessage());
		}

		final Map<Key<Thread>, Thread> threadMap = threadRepository
				.findAll(threadKeys);
		final Map<Key<Message>, Message> messageMap = messageMessageRepository
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
	public int getUnreadCount(@NotNull final Member member) {
		return messageLeafRepository.countUnreadByOwner(member);
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
		final Message message = createTextMessage(sender, thread, text);
		thread.getMessages().add(Ref.create(message));
		threadRepository.save(thread);

		for (final Ref<Member> participant : thread.getActiveParticipants()) {
			queueManager.deliverMessage(participant.getKey(),
					Key.create(thread), Key.create(message));
		}
		return message;
	}

	@Override
	public void sendAsync(final Member recipient, final Thread thread,
			final Message message) {
		final ThreadLeaf memberThread = createOrGetThreadLeaf(recipient, thread);
		final MessageLeaf memberMessage = createMessageLeaf(recipient, message,
				thread);

		memberThread.setLastMessage(Key.create(message));
		memberThread.setLastUpdate(new Date());
		memberThread.setUnread(null == memberMessage.getReadTimestamp());
		memberThread.getMessages().add(Ref.create(memberMessage));
		threadLeafRepository.save(memberThread);
	}

}
