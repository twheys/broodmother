package com.heys.dating.test;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.heys.dating.test.config.GoogleAppEngineConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = GoogleAppEngineConfig.class, loader = AnnotationConfigContextLoader.class)
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
