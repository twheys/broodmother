package com.heys.dating.message;

public interface BlacklistService {

	/**
	 * Add a user to another user's blacklist
	 * 
	 * @param member
	 *            The member who own's the blacklist
	 * @param target
	 *            The member being added to the blacklist
	 */
	void add(String member, String target);

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
	 * @param target
	 *            The member being checked
	 * @return True if the target member is on the member's blacklist
	 */
	boolean isBlacklisted(String member, String target);

	/**
	 * Remove a user to another user's blacklist
	 * 
	 * @param member
	 *            The member who own's the blacklist
	 * @param target
	 *            The member being removed from the blacklist
	 */
	void remove(String member, String target);
}
