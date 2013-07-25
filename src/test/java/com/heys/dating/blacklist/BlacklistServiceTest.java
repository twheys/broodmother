package com.heys.dating.blacklist;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import org.junit.Before;
import org.junit.Test;

import com.heys.dating.impl.app.BlacklistServiceImpl;
import com.heys.dating.impl.gae.repository.BlacklistRepositoryDatastoreImpl;
import com.heys.dating.message.BlacklistRepository;
import com.heys.dating.message.BlacklistService;
import com.heys.dating.test.GaeTest;

public class BlacklistServiceTest extends GaeTest {

	private BlacklistService blacklistSvc;

	@Test
	public void addAndCheck() {
		final String testUser = "testUser01";
		final String blacklistedUser1 = "blacklistedUser01";
		final String blacklistedUser2 = "blacklistedUser02";

		blacklistSvc.add(testUser, blacklistedUser1);

		assertTrue(blacklistSvc.isBlacklisted(testUser, blacklistedUser1));
		assertFalse(blacklistSvc.isBlacklisted(testUser, blacklistedUser2));
	}

	@Test
	public void clearBlacklist() {
		final String testUser = "testUser02";
		final String blacklistedUser1 = "blacklistedUser01";
		final String blacklistedUser2 = "blacklistedUser02";

		blacklistSvc.add(testUser, blacklistedUser1);
		blacklistSvc.add(testUser, blacklistedUser2);

		assertTrue(blacklistSvc.isBlacklisted(testUser, blacklistedUser1));
		assertTrue(blacklistSvc.isBlacklisted(testUser, blacklistedUser2));

		blacklistSvc.clear(testUser);

		assertFalse(blacklistSvc.isBlacklisted(testUser, blacklistedUser1));
		assertFalse(blacklistSvc.isBlacklisted(testUser, blacklistedUser2));
	}

	@Test
	public void removeOne() {
		final String testUser = "testUser03";
		final String blacklistedUser1 = "blacklistedUser01";
		final String blacklistedUser2 = "blacklistedUser02";

		blacklistSvc.add(testUser, blacklistedUser1);
		blacklistSvc.add(testUser, blacklistedUser2);

		assertTrue(blacklistSvc.isBlacklisted(testUser, blacklistedUser1));
		assertTrue(blacklistSvc.isBlacklisted(testUser, blacklistedUser2));

		blacklistSvc.remove(testUser, blacklistedUser2);

		assertTrue(blacklistSvc.isBlacklisted(testUser, blacklistedUser1));
		assertFalse(blacklistSvc.isBlacklisted(testUser, blacklistedUser2));
	}

	@Override
	@Before
	public void setUp() {
		super.setUp();
		blacklistSvc = new BlacklistServiceImpl();
		final BlacklistRepository blacklistRepository = new BlacklistRepositoryDatastoreImpl();
		setField(blacklistSvc, "blacklistRepository", blacklistRepository);
	}
}
