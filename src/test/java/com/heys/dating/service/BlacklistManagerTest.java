package com.heys.dating.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import org.junit.Before;
import org.junit.Test;

import com.heys.dating.domain.repository.BlacklistRepository;
import com.heys.dating.domain.repository.impl.BlacklistRepositoryDatastoreImpl;
import com.heys.dating.manager.BlacklistManager;
import com.heys.dating.manager.impl.BlacklistManagerImpl;
import com.heys.dating.test.GaeTest;

public class BlacklistManagerTest extends GaeTest {

	private BlacklistManager blacklistManager;

	@Test
	public void addAndCheck() {
		final String testUser = "testUser01";
		final String blacklistedUser1 = "blacklistedUser01";
		final String blacklistedUser2 = "blacklistedUser02";

		blacklistManager.add(testUser, blacklistedUser1);

		assertTrue(blacklistManager.isBlacklisted(testUser, blacklistedUser1));
		assertFalse(blacklistManager.isBlacklisted(testUser, blacklistedUser2));
	}

	@Test
	public void clearBlacklist() {
		final String testUser = "testUser02";
		final String blacklistedUser1 = "blacklistedUser01";
		final String blacklistedUser2 = "blacklistedUser02";

		blacklistManager.add(testUser, blacklistedUser1);
		blacklistManager.add(testUser, blacklistedUser2);

		assertTrue(blacklistManager.isBlacklisted(testUser, blacklistedUser1));
		assertTrue(blacklistManager.isBlacklisted(testUser, blacklistedUser2));

		blacklistManager.clear(testUser);

		assertFalse(blacklistManager.isBlacklisted(testUser, blacklistedUser1));
		assertFalse(blacklistManager.isBlacklisted(testUser, blacklistedUser2));
	}

	@Test
	public void removeOne() {
		final String testUser = "testUser03";
		final String blacklistedUser1 = "blacklistedUser01";
		final String blacklistedUser2 = "blacklistedUser02";

		blacklistManager.add(testUser, blacklistedUser1);
		blacklistManager.add(testUser, blacklistedUser2);

		assertTrue(blacklistManager.isBlacklisted(testUser, blacklistedUser1));
		assertTrue(blacklistManager.isBlacklisted(testUser, blacklistedUser2));

		blacklistManager.remove(testUser, blacklistedUser2);

		assertTrue(blacklistManager.isBlacklisted(testUser, blacklistedUser1));
		assertFalse(blacklistManager.isBlacklisted(testUser, blacklistedUser2));
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		blacklistManager = new BlacklistManagerImpl();
		final BlacklistRepository blacklistRepository = new BlacklistRepositoryDatastoreImpl();
		setField(blacklistManager, "blacklistRepository", blacklistRepository);
	}
}
