package com.heys.dating.test;

import org.junit.After;
import org.junit.Before;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class GaeTest {
	protected final LocalServiceTestHelper helper;

	public GaeTest() {
		final LocalDatastoreServiceTestConfig dsConfig = new LocalDatastoreServiceTestConfig();
		final LocalMemcacheServiceTestConfig msConfig = new LocalMemcacheServiceTestConfig();
		helper = new LocalServiceTestHelper(dsConfig, msConfig);
	}

	@Before
	public void setUp() {
		helper.setUp();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

}
