package com.heys.dating.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.heys.dating.blacklist.BlacklistServiceTest;
import com.heys.dating.deeplink.DeeplinkServiceTest;
import com.heys.dating.member.MemberServiceTest;
import com.heys.dating.message.MessageServiceTest;

@RunWith(Suite.class)
@SuiteClasses({ MemberServiceTest.class, MessageServiceTest.class,
		BlacklistServiceTest.class, DeeplinkServiceTest.class })
public class ApiTestSuite {

}
