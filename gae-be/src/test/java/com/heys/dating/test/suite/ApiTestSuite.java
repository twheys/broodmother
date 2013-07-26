package com.heys.dating.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.heys.dating.blacklist.BlacklistServiceTest;
import com.heys.dating.deeplink.DeeplinkServiceTest;
import com.heys.dating.member.MemberServiceTest;
import com.heys.dating.message.MessageServiceTest;
import com.heys.dating.profile.ProfileServiceTest;

@RunWith(Suite.class)
@SuiteClasses({ MemberServiceTest.class, MessageServiceTest.class,
		BlacklistServiceTest.class, DeeplinkServiceTest.class,
		ProfileServiceTest.class })
public class ApiTestSuite {

}
