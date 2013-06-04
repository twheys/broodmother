package com.heys.dating.util;

import org.apache.commons.lang.StringUtils;

public class EnumUtils {

	@SuppressWarnings("unchecked")
	public static <T extends Enum<T>> T toEnum(final String value,
			final T defaultValue) {
		return (T) Enum.valueOf((Class) defaultValue.getClass(),
				StringUtils.upperCase(value));
	}
}
