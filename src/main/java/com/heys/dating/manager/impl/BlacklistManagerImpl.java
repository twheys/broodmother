package com.heys.dating.manager.impl;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heys.dating.domain.member.BlacklistEntry;
import com.heys.dating.domain.repository.BlacklistRepository;
import com.heys.dating.manager.BlacklistManager;

@Service("BlacklistManager")
@Slf4j
public final class BlacklistManagerImpl implements BlacklistManager {

	@Autowired
	private BlacklistRepository blacklistRepository;

	@Override
	public void add(final String member, final String target) {
		log.info("Adding {} to {}'s blacklist.", target, member);

		val blEntry = new BlacklistEntry(member, target);
		blacklistRepository.saveAsync(blEntry);
	}

	@Override
	public void clear(final String member) {
		log.info("Clearing {}'s blacklist.", member);

		blacklistRepository.deleteByMember(member);
	}

	@Override
	public boolean isBlacklisted(final String member, final String targetMember) {
		log.info("Checking if {} has {} blacklisted.", member, targetMember);

		int entryCount = blacklistRepository.countByMemberAndTarget(member,
				targetMember);
		return 0 != entryCount;
	}

	@Override
	public void remove(final String member, final String target) {
		log.info("Removing {} from {}'s blacklist.", target, member);

		blacklistRepository.deleteByMemberAndTarget(member, target);
	}

}
