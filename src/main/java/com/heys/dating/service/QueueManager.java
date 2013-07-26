package com.heys.dating.service;

import com.googlecode.objectify.Key;
import com.heys.dating.member.Member;
import com.heys.dating.message.Message;
import com.heys.dating.message.Thread;

public interface QueueManager {

	void deliverMessage(final Key<Member> senderKey,
			final Key<Thread> threadKey, final Key<Message> messageKey);

}
