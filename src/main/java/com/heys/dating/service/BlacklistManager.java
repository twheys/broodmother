package com.heys.dating.service;

public interface BlacklistManager {

	/**
	 * Add a user to another user's blacklist
	 * 
	 * @param member
	 *            The member who own's the blacklist
	 * @param targetMember
	 *            The member being added to the blacklist
	 */
	void add(String member, String targetMember);

	/**
	 * Clear a member's blacklist.
	 * 
	 * @param member
	 *            The member who own's the blacklist
	 */
	void clear(String member);

	/**
	 * Check if a member is on another member's blacklist.
	 * 
	 * @param member
	 *            The member who own's the blacklist
	 * @param targetMember
	 *            The member being checked
	 * @return True if the target member is on the member's blacklist
	 */
	boolean isBlacklisted(String member, String targetMember);

	/**
	 * Remove a user to another user's blacklist
	 * 
	 * @param member
	 *            The member who own's the blacklist
	 * @param targetMember
	 *            The member being removed from the blacklist
	 */
	void remove(String member, String targetMember);
}
