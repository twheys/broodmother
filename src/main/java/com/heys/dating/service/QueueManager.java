package com.heys.dating.service;

import com.googlecode.objectify.Key;
import com.heys.dating.domain.member.Member;
import com.heys.dating.domain.message.Message;

public interface QueueManager {

	void deliverMessage(String sharedThreadID, Key<Member> member,
			Key<Message> message);

	// void indexProfile(Profile profile);
	//
	// void notify(Member member, Notification notification);

}
