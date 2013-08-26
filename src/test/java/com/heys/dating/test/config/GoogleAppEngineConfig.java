package com.heys.dating.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.heys.dating.deeplink.DeeplinkRepository;
import com.heys.dating.deeplink.DeeplinkService;
import com.heys.dating.impl.app.BlacklistServiceImpl;
import com.heys.dating.impl.app.DeeplinkServiceImpl;
import com.heys.dating.impl.app.MemberServiceImpl;
import com.heys.dating.impl.app.MessageServiceImpl;
import com.heys.dating.impl.app.MessageTransformerImpl;
import com.heys.dating.impl.app.PictureServiceImpl;
import com.heys.dating.impl.app.ProfileServiceImpl;
import com.heys.dating.impl.gae.SearchManagerAppEngineImpl;
import com.heys.dating.impl.repository.BlacklistRepositoryDatastoreImpl;
import com.heys.dating.impl.repository.DeeplinkRepositoryDatastoreImpl;
import com.heys.dating.impl.repository.MemberRepositoryDatastoreImpl;
import com.heys.dating.impl.repository.MessageLeafRepositoryDatastoreImpl;
import com.heys.dating.impl.repository.MessageRepositoryDatastoreImpl;
import com.heys.dating.impl.repository.ProfileRepositoryDatastoreImpl;
import com.heys.dating.impl.repository.ThreadLeafRepositoryDatastoreImpl;
import com.heys.dating.impl.repository.ThreadRepositoryDatastoreImpl;
import com.heys.dating.member.MemberRepository;
import com.heys.dating.member.MemberService;
import com.heys.dating.message.BlacklistRepository;
import com.heys.dating.message.BlacklistService;
import com.heys.dating.message.MessageLeafRepository;
import com.heys.dating.message.MessageRepository;
import com.heys.dating.message.MessageService;
import com.heys.dating.message.MessageTransformer;
import com.heys.dating.message.ThreadLeafRepository;
import com.heys.dating.message.ThreadRepository;
import com.heys.dating.picture.PictureService;
import com.heys.dating.profile.ProfileRepository;
import com.heys.dating.profile.ProfileService;
import com.heys.dating.service.QueueManager;
import com.heys.dating.service.SearchManager;
import com.heys.dating.test.TestQueueManager;

@Configuration
public class GoogleAppEngineConfig implements GoDatingTestingConfig {

	@Override
	@Bean
	public BlacklistRepository blacklistRepository() {
		return new BlacklistRepositoryDatastoreImpl();
	}

	@Override
	@Bean
	public BlacklistService blacklistService() {
		return new BlacklistServiceImpl();
	}

	@Override
	@Bean
	public DeeplinkRepository deeplinkRepository() {
		return new DeeplinkRepositoryDatastoreImpl();
	}

	@Override
	@Bean
	public DeeplinkService deeplinkService() {
		return new DeeplinkServiceImpl();
	}

	@Override
	@Bean
	public MessageLeafRepository memberMessageRepository() {
		return new MessageLeafRepositoryDatastoreImpl();
	}

	@Override
	@Bean
	public MemberRepository memberRepository() {
		return new MemberRepositoryDatastoreImpl();
	}

	@Override
	@Bean
	public MemberService memberService() {
		return new MemberServiceImpl();
	}

	@Override
	@Bean
	public ThreadLeafRepository memberThreadRepository() {
		return new ThreadLeafRepositoryDatastoreImpl();
	}

	@Override
	@Bean
	public MessageRepository messageRepository() {
		return new MessageRepositoryDatastoreImpl();
	}

	@Override
	@Bean
	public MessageService messageService() {
		return new MessageServiceImpl();
	}

	@Override
	@Bean
	public MessageTransformer messageTransformer() {
		return new MessageTransformerImpl();
	}

	@Override
	@Bean
	public PictureService pictureService() {
		return new PictureServiceImpl();
	}

	@Override
	@Bean
	public ProfileRepository profileRepository() {
		return new ProfileRepositoryDatastoreImpl();
	}

	@Override
	@Bean
	public ProfileService profileService() {
		return new ProfileServiceImpl();
	}

	@Override
	@Bean
	public QueueManager queueManager() {
		return new TestQueueManager();
	}

	@Override
	public SearchManager searchManager() {
		return new SearchManagerAppEngineImpl();
	}

	@Override
	@Bean
	public ThreadRepository threadRepository() {
		return new ThreadRepositoryDatastoreImpl();
	}
}
