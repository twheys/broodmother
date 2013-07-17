package com.heys.dating.manager;

import com.googlecode.objectify.Key;
import com.heys.dating.domain.member.Member;
import com.heys.dating.domain.message.Message;
import com.heys.dating.domain.message.Thread;

public interface QueueManager {

	void deliverMessage(final Key<Member> senderKey,
			final Key<Thread> threadKey, final Key<Message> messageKey);

}
