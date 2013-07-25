package com.heys.dating.util;

import com.heys.dating.member.Gender;
import com.heys.dating.picture.Picture;

public class UrlUtil {

	private static final String PREVIEW = "/preview.png";
	private static final String ICON = "/icon.png";
	private static final String FULL = "/full.png";

	public static String getProfileFullUrl(final Picture profilePic,
			final Gender gender) {
		return pictureUrl(profilePic, gender).append(FULL).toString();
	}

	public static String getProfileIconUrl(final Picture profilePic,
			final Gender gender) {
		return pictureUrl(profilePic, gender).append(ICON).toString();
	}

	public static String getProfilePreviewUrl(final Picture profilePic,
			final Gender gender) {
		return pictureUrl(profilePic, gender).append(PREVIEW).toString();
	}

	private static StringBuilder pictureUrl(final Picture profilePic,
			final Gender gender) {
		final StringBuilder sb = new StringBuilder();
		if (null == profilePic) {
			sb.append("/r/");
			if (null == gender) {
				sb.append("default");
			} else {
				sb.append(gender.name().toLowerCase());
			}
		} else {
			sb.append("/p/");
			sb.append(profilePic.getId().toString());
		}
		return sb;
	}

}
