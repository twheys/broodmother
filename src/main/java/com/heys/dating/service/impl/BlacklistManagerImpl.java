package com.heys.dating.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heys.dating.domain.member.BlacklistEntry;
import com.heys.dating.domain.repository.BlacklistRepository;
import com.heys.dating.service.BlacklistManager;

@Service("BlacklistManager")
public class BlacklistManagerImpl implements BlacklistManager {
	@Autowired
	private BlacklistRepository blacklistRepository;

	@Override
	public void add(final String member, final String targetMember) {
		blacklistRepository.saveAsync(new BlacklistEntry(member.toLowerCase(),
				targetMember.toLowerCase()));
	}

	@Override
	public void clear(final String member) {
		blacklistRepository.deleteByMember(member);
	}

	@Override
	public boolean isBlacklisted(final String member, final String targetMember) {
		return null != blacklistRepository.findByMemberAndTargetMember(member,
				targetMember);
	}

	@Override
	public void remove(final String member, final String targetMember) {
		blacklistRepository.deleteByMemberAndTargetMember(member, targetMember);
	}

}
