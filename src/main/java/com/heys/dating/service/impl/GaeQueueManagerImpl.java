package com.heys.dating.service.impl;

import static com.google.appengine.api.taskqueue.TaskOptions.Builder.withUrl;

import org.springframework.stereotype.Service;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.googlecode.objectify.Key;
import com.heys.dating.domain.member.Member;
import com.heys.dating.domain.message.Message;
import com.heys.dating.service.QueueManager;

@Service("QueueManager")
public class GaeQueueManagerImpl implements QueueManager {

	private static final String DELIVER_MSG_URL = "/job/deliver_message";

	private final Queue queue = QueueFactory.getDefaultQueue();

	//
	// @Override
	// public void deliverMessage(final Member to, final Message message) {
	// final Long memberKey = to.getId();
	// final Long messageKey = message.getId();
	// queue.add(withUrl(DELIVER_MSG_URL).param("member_key", "" + memberKey)
	// .param("message_key", "" + messageKey));
	//
	// }
	//
	// @Override
	// public void indexProfile(final Key<Profile> profile) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void notify(final Member member, final Notification notification)
	// {
	// // final String memberKey = KeyFactory.keyToString(to.getKey());
	// // final String messageKey = KeyFactory.keyToString(message.getKey());
	// // queue.add(withUrl(NOTIFY_MSG_URL).param("member_key",
	// // memberKey).param(
	// // "message_key", messageKey));
	// }

	@Override
	public void deliverMessage(final String sharedThreadID,
			final Key<Member> memberKey, final Key<Message> messageKey) {
		queue.add(withUrl(DELIVER_MSG_URL)
				.param("s-thread-id", sharedThreadID)
				.param("recipient-id", memberKey.getString())
				.param("message-id", messageKey.getString()));

	}

}
