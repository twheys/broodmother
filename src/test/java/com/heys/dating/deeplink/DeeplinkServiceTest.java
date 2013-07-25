package com.heys.dating.deeplink;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.appengine.repackaged.org.joda.time.DateTime;
import com.heys.dating.member.Member;
import com.heys.dating.test.MemberTest;

public class DeeplinkServiceTest extends MemberTest {

	@Autowired
	private DeeplinkService deeplinkSvc;

	@Test
	public void createAndRedeem() throws InvalidDeeplinkException,
			InterruptedException {
		final Member member = shortReg(getShortRegValues());
		final DeeplinkAction action = DeeplinkAction.ACTIVATION;

		final String deeplinkValue = deeplinkSvc.generate(member, action,
				new DateTime().plusDays(1).toDate());
		Thread.sleep(100L);
		final Deeplink deeplink = deeplinkSvc.parse(deeplinkValue);

		assertNotNull(deeplink);
		assertEquals(member, deeplink.getMember().get());
		assertEquals(action, deeplink.getAction());
	}

	@Test(expected = InvalidDeeplinkException.class)
	public void createAndRedeemExpired() throws InvalidDeeplinkException,
			InterruptedException {
		final Member member = shortReg(getShortRegValues());
		final DeeplinkAction action = DeeplinkAction.ACTIVATION;

		final String deeplinkValue = deeplinkSvc.generate(member, action,
				new DateTime().minusDays(1).toDate());
		Thread.sleep(100L);
		deeplinkSvc.parse(deeplinkValue);
		fail();
	}

	@Test(expected = InvalidDeeplinkException.class)
	public void createAndRedeemOnlyOnce() throws InvalidDeeplinkException,
			InterruptedException {
		final Member member = shortReg(getShortRegValues());
		final DeeplinkAction action = DeeplinkAction.ACTIVATION;

		final String deeplinkValue = deeplinkSvc.generate(member, action,
				new DateTime().plusDays(1).toDate());
		Thread.sleep(100L);
		final Deeplink deeplink = deeplinkSvc.parse(deeplinkValue);
		assertNotNull(deeplink);
		deeplinkSvc.parse(deeplinkValue);
		fail();
	}

}
