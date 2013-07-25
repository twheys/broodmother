package com.heys.dating.impl.app;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heys.dating.message.BlacklistEntry;
import com.heys.dating.message.BlacklistRepository;
import com.heys.dating.message.BlacklistService;

@Service("BlacklistService")
@Slf4j
public final class BlacklistServiceImpl implements BlacklistService {

	@Autowired
	private BlacklistRepository blacklistRepository;

	@Override
	public void add(final String member, final String target) {
		log.info("Adding {} to {}'s blacklist.", target, member);

		val blEntry = new BlacklistEntry(member, target);
		blacklistRepository.save(blEntry);
	}

	@Override
	public void clear(final String member) {
		log.info("Clearing {}'s blacklist.", member);

		blacklistRepository.deleteByMember(member);
	}

	@Override
	public boolean isBlacklisted(final String member, final String targetMember) {
		log.info("Checking if {} has {} blacklisted.", member, targetMember);

		final int entryCount = blacklistRepository.countByMemberAndTarget(
				member, targetMember);
		return 0 != entryCount;
	}

	@Override
	public void remove(final String member, final String target) {
		log.info("Removing {} from {}'s blacklist.", target, member);

		blacklistRepository.deleteByMemberAndTarget(member, target);
	}

}
