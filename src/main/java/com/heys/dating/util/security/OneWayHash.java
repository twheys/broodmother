package com.heys.dating.util.security;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import com.google.common.base.Joiner;

public class OneWayHash {

	private final String password;

	public OneWayHash(final String... passwords) {
		password = Joiner.on("").join(Arrays.asList(passwords));
	}

	public String encryptBase64(final String unencryptedString) {
		final StringBuilder sb = new StringBuilder(password)
				.append(unencryptedString);
		try {
			final byte[] unencryptedByteArray = sb.toString().getBytes("UTF8");
			final byte[] encryptedBytes = DigestUtils.md5(unencryptedByteArray);
			final byte[] encodedBytes = Base64
					.encodeBase64URLSafe(encryptedBytes);
			return new String(encodedBytes);
		} catch (final UnsupportedEncodingException e) {
			throw new IllegalStateException("Encoding error!", e);
		}
	}

}
