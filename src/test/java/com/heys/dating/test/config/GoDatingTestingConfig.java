package com.heys.dating.test.config;

import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder;

import com.heys.dating.deeplink.DeeplinkRepository;
import com.heys.dating.deeplink.DeeplinkService;
import com.heys.dating.member.MemberRepository;
import com.heys.dating.member.MemberService;
import com.heys.dating.message.BlacklistRepository;
import com.heys.dating.message.BlacklistService;
import com.heys.dating.message.MessageLeafRepository;
import com.heys.dating.message.ThreadLeafRepository;
import com.heys.dating.message.MessageRepository;
import com.heys.dating.message.MessageService;
import com.heys.dating.message.MessageTransformer;
import com.heys.dating.message.ThreadRepository;
import com.heys.dating.picture.PictureService;
import com.heys.dating.profile.ProfileRepository;
import com.heys.dating.profile.ProfileService;
import com.heys.dating.service.QueueManager;
import com.heys.dating.service.SearchManager;

public interface GoDatingTestingConfig {

	BlacklistRepository blacklistRepository();

	BlacklistService blacklistService();

	DeeplinkService deeplinkService();

	DeeplinkRepository deeplinkRepository();

	MessageLeafRepository memberMessageRepository();

	MemberRepository memberRepository();

	MemberService memberService();

	ThreadLeafRepository memberThreadRepository();

	MessageRepository messageRepository();

	MessageService messageService();

	MessageTransformer messageTransformer();

	ProfileRepository profileRepository();

	QueueManager queueManager();

	SearchManager searchManager();

	ThreadRepository threadRepository();

	ReflectionSaltSource saltSource();

	PictureService pictureService();

	PlaintextPasswordEncoder passwordEncoder();

	ProfileService profileService();

}
