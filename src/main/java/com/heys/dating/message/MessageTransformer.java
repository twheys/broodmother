package com.heys.dating.message;

import java.util.Map;

import com.googlecode.objectify.Key;
import com.heys.dating.member.Member;
import com.heys.dating.message.dto.ThreadDTO;
import com.heys.dating.message.dto.ThreadListDTO;

public interface MessageTransformer {

	ThreadDTO transformThread(final Thread thread,
			final Iterable<MemberMessage> memberMessages,
			final Map<Key<Message>, Message> messageMap,
			final Map<Key<Member>, Member> memberMap,
			final Map<Key<Member>, String> profileMap,
			final Map<Key<Member>, Long> pictureMap);

	ThreadListDTO transformThreadList(
			final Iterable<MemberThread> memberThreads,
			final Map<Key<Thread>, Thread> threadMap,
			final Map<Key<Message>, Message> messageMap,
			final Map<Key<Member>, Member> senderMap);

}
