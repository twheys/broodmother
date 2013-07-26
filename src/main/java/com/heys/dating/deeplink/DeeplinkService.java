package com.heys.dating.deeplink;

import java.util.Date;

import com.heys.dating.member.Member;

public interface DeeplinkService {

	/**
	 * Generates a deeplink for a member and action with an expiration date.
	 */
	public String generate(final Member member, final DeeplinkAction action,
			final Date expiration);

	/**
	 * Retrieves and deletes the valid deeplink record. If the deeplink is
	 * invalid or expired, throws an {@link InvalidDeeplinkException}
	 */
	public Deeplink parse(final String deeplink)
			throws InvalidDeeplinkException;

}
