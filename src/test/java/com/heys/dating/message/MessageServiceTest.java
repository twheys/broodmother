package com.heys.dating.message;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.appengine.labs.repackaged.com.google.common.collect.Lists;
import com.heys.dating.member.Member;
import com.heys.dating.message.Message;
import com.heys.dating.message.MessageService;
import com.heys.dating.message.Thread;
import com.heys.dating.message.dto.MessageDTO;
import com.heys.dating.message.dto.ParticipantDTO;
import com.heys.dating.message.dto.ThreadDTO;
import com.heys.dating.message.dto.ThreadListDTO;
import com.heys.dating.message.dto.ThreadPreviewDTO;
import com.heys.dating.test.MemberTest;
import com.heys.dating.util.DatastoreUtil;

public class MessageServiceTest extends MemberTest {

	@Autowired
	private MessageService messageSvc;

	private void assertMessages(final Member recipient, final Thread thread,
			final Member lastSender, final String lastText) {
		final ThreadDTO threadDTO = messageSvc.getMessagesForThread(recipient,
				thread, 10, 0);
		assertNotNull(threadDTO);
		assertEquals(thread.getSubject(), threadDTO.getSubject());
		assertNotNull(threadDTO.getStartDate());

		final List<MessageDTO> messages = threadDTO.getMessages();
		assertNotNull(messages);
		assertFalse(messages.isEmpty());
		assertTrue(10 >= messages.size());

		final MessageDTO messageDTO = messages.get(0);
		assertEquals(lastSender.getId(), messageDTO.getParticipantId());
		assertTrue(messageDTO.getPictureUrls().isEmpty());
		assertNotNull(messageDTO.getSentTime());
		assertNull(messageDTO.getReadTime());
		assertEquals(lastText, messageDTO.getText());

		final List<ParticipantDTO> participants = threadDTO.getParticipants();
		assertNotNull(participants);
		assertFalse(participants.isEmpty());
		for (final ParticipantDTO participant : participants) {
			if (messageDTO.getParticipantId() == participant.getId()) {
				assertEquals(lastSender.getLogin(), participant.getLogin());
				assertNotNull(participant.getProfileVanity());
			}
		}
	}

	private void assertThreadPreview(final Member lastSender,
			final Member recipient, final String subject, final String lastText) {
		final ThreadListDTO threadListDTO = messageSvc.getNewThreadsForMember(
				recipient, 10, 0);
		assertNotNull(threadListDTO);
		final List<ThreadPreviewDTO> threadPreviews = threadListDTO
				.getThreadPreviews();
		assertNotNull(threadPreviews);
		assertFalse(threadPreviews.isEmpty());
		assertTrue(10 >= threadPreviews.size());

		final ThreadPreviewDTO threadPreview = threadPreviews.get(0);
		assertNotNull(threadPreview);

		assertNotNull(threadPreview.getThreadId());
		assertNotNull(threadPreview.getLastUpdated());
		assertEquals(subject, threadPreview.getSubject());
		assertEquals(lastText, threadPreview.getLastMessage());
		assertEquals(lastSender.getLogin(), threadPreview.getLastSender());
		assertTrue(threadPreview.getParticipants().contains(
				lastSender.getLogin()));
		assertTrue(threadPreview.getParticipants().contains(
				recipient.getLogin()));
	}

	private void assertThreadPreviews(final Member sender,
			final String subject, final String text,
			final Iterable<Member> participants) {
		assertThreadPreview(sender, sender, subject, text);
		for (final Member participant : participants) {
			assertThreadPreview(sender, participant, subject, text);
		}
	}

	@Test
	public void messages_Direct() {
		final Member participant1 = longReg(getLongRegValues());
		final Member participant2 = longReg(getLongRegValues());
		final String text1 = "Hello! Let's have a conversation.";
		final String text2 = "Hi! Okay. What would you like to talk about?";
		final String text3 = "I don't know! Do you have a suggestion?";
		final String text4 = "No... I can't think of anything.";
		final String text5 = "Think about it.";
		final String text6 = "OK... thinking.";
		final String text7 = "Got anything?";
		final String text8 = "Nope. Sorry.";
		final String text9 = "Well, okay. Maybe next time.";
		final String text10 = "Alright, talk to you later.";
		final String text11 = "Bye!";

		final Thread thread = testMessages(participant1, text1, participant2);
		testMessages(participant2, text2, thread);
		testMessages(participant1, text3, thread);
		testMessages(participant2, text4, thread);
		testMessages(participant1, text5, thread);
		testMessages(participant2, text6, thread);
		testMessages(participant1, text7, thread);
		testMessages(participant2, text8, thread);
		testMessages(participant1, text9, thread);
		testMessages(participant2, text10, thread);
		testMessages(participant1, text11, thread);
	}

	@Test
	public void messages_Group() {
		final Member participant1 = longReg(getLongRegValues());
		final Member participant2 = longReg(getLongRegValues());
		final Member participant3 = longReg(getLongRegValues());
		final String text1 = "Hello! I hope you all receive this message.";
		final String text2 = "Hi! I did get your message.";
		final String text3 = "Hey! I also got your message.";

		final Thread thread = testMessages(participant1, text1, participant2,
				participant3);
		testMessages(participant2, text2, thread);
		testMessages(participant3, text3, thread);
	}

	private Message sendMessage(final Member sender, final String subject,
			final String text, final Member... participants) {
		final Message message;
		final boolean directMessage = 1 == participants.length;
		final boolean subjectPresent = null != subject;
		if (directMessage && subjectPresent) {
			message = messageSvc.send(sender, participants[0], subject, text);
		} else if (directMessage) {
			message = messageSvc.send(sender, participants[0], text);
		} else if (subjectPresent) {
			message = messageSvc.send(sender, Lists.newArrayList(participants),
					subject, text);
		} else {
			message = messageSvc.send(sender, Lists.newArrayList(participants),
					text);
		}
		return message;
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	private Thread testMessages(final Member sender, final String text,
			final Member... participants) {
		return testMessages(sender, null, text, participants);
	}

	private Thread testMessages(final Member sender, final String subject,
			final String text, final Member... participants) {
		final Message message = sendMessage(sender, null, text, participants);
		final Thread thread = message.getThread().get();
		assertMessages(sender, thread, sender, text);
		return thread;
	}

	private void testMessages(final Member sender, final String text,
			final Thread thread) {
		messageSvc.send(sender, thread, text);
		assertMessages(sender, thread, sender, text);
	}

	private Thread testThreadPreviews(final Member sender, final String text,
			final Member... participants) {
		return testThreadPreviews(sender, null, text, participants);
	}

	private Thread testThreadPreviews(final Member sender,
			final String subject, final String text,
			final Member... participants) {
		final Message message = sendMessage(sender, subject, text, participants);
		assertThreadPreviews(sender, subject, text,
				Lists.newArrayList(participants));
		return message.getThread().get();
	}

	private void testThreadPreviews(final Member sender, final String text,
			final Thread thread) {
		messageSvc.send(sender, thread, text);
		final Iterable<Member> participants = DatastoreUtil.values(thread
				.getActiveParticipants());
		assertThreadPreviews(sender, thread.getSubject(), text, participants);
	}

	@Test
	public void threadPreviews_MultipleThreads_WithSubject() {
		final Member participant1 = longReg(getLongRegValues());
		final Member participant2 = longReg(getLongRegValues());
		final Member participant3 = longReg(getLongRegValues());
		final String text1 = "Hello! I hope you all receive this message.";
		final String text2 = "Hi! I did get your message.";
		final String text3 = "Hey! I also got your message.";

		final Thread thread1 = testThreadPreviews(participant1, "Thread 1",
				text1, participant2, participant3);
		testThreadPreviews(participant2, text2, thread1);
		testThreadPreviews(participant3, text3, thread1);

		final String text4 = "Here is a direct message.";
		final String text5 = "I got this message too.";

		final Thread thread2 = testThreadPreviews(participant1, "Thread 2",
				text4, participant2);
		testThreadPreviews(participant2, text5, thread2);

	}

	@Test
	public void threadPreviews_NewThread_Direct() {
		final Member participant1 = longReg(getLongRegValues());
		final Member participant2 = longReg(getLongRegValues());
		final String text = "Hello! I hope you receive this message.";

		testThreadPreviews(participant1, null, text, participant2);
	}

	@Test
	public void threadPreviews_NewThread_Group() {
		final Member participant1 = longReg(getLongRegValues());
		final Member participant2 = longReg(getLongRegValues());
		final Member participant3 = longReg(getLongRegValues());
		final String text = "Hello! I hope you all receive this message.";

		testThreadPreviews(participant1, text, participant2, participant3);
	}

	@Test
	public void threadPreviews_Replies_Direct() {
		final Member participant1 = longReg(getLongRegValues());
		final Member participant2 = longReg(getLongRegValues());
		final String text1 = "Hello! I hope you receive this message.";
		final String text2 = "Hi! I did get your message.";

		final Thread thread = testThreadPreviews(participant1, text1,
				participant2);
		testThreadPreviews(participant2, text2, thread);
	}

	@Test
	public void threadPreviews_Replies_Group() {
		final Member participant1 = longReg(getLongRegValues());
		final Member participant2 = longReg(getLongRegValues());
		final Member participant3 = longReg(getLongRegValues());
		final String text1 = "Hello! I hope you all receive this message.";
		final String text2 = "Hi! I did get your message.";
		final String text3 = "Hey! I also got your message.";

		final Thread thread = testThreadPreviews(participant1, text1,
				participant2, participant3);
		testThreadPreviews(participant2, text2, thread);
		testThreadPreviews(participant3, text3, thread);
	}
}
