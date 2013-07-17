package com.heys.dating.service;

import java.util.Map;

import com.googlecode.objectify.Key;
import com.heys.dating.domain.member.Member;
import com.heys.dating.domain.message.MemberMessage;
import com.heys.dating.domain.message.MemberThread;
import com.heys.dating.domain.message.Message;
import com.heys.dating.domain.message.Thread;
import com.heys.dating.service.dto.message.ThreadDTO;
import com.heys.dating.service.dto.message.ThreadListDTO;

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
