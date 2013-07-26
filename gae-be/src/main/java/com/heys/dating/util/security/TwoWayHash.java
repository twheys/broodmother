package com.heys.dating.util.security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.google.common.base.Preconditions;

/**
 * Using <tt>DES</tt> algorithm for two-way hash.
 * 
 */
public class TwoWayHash {

	private static final String DES = "DES";
	private final String algorithm = DES;

	private Cipher decryptCipher;
	private Cipher encryptCipher;

	private final SecretKey secretKey;
	private final SecretKeyFactory secretKeyFactory;

	public TwoWayHash(final String value) {
		Preconditions.checkNotNull(value, "Password cannot be null");

		try {
			secretKeyFactory = SecretKeyFactory.getInstance(algorithm);
			secretKey = secretKeyFactory.generateSecret(new DESKeySpec(value
					.getBytes()));
		} catch (final Exception e) {
			throw new IllegalStateException(String.format(
					"Error creating two-way hash for: %s", DES), e);
		}

	}

	/**
	 * Decrypts a BASE64 encoded, DES encrypted string and returns the
	 * unencrypted string.
	 */
	public String decryptBase64(final String encryptedString) {
		try {
			final byte[] decodedBytes = Base64.decodeBase64(encryptedString
					.getBytes());
			final byte[] unencryptedByteArray = getDecryptCipher().doFinal(
					decodedBytes);
			return new String(unencryptedByteArray, "UTF8");
		} catch (final Exception e) {
			throw new IllegalStateException("Decryption error!", e);
		}
	}

	/**
	 * Encrypts a string using DES encryption, and returns the encrypted string
	 * as a BASE64 encoded string.
	 */
	public String encryptBase64(final String unencryptedString) {
		try {
			final byte[] unencryptedByteArray = unencryptedString
					.getBytes("UTF8");
			final byte[] encryptedBytes = getEncryptCipher().doFinal(
					unencryptedByteArray);
			final byte[] encodedBytes = Base64
					.encodeBase64URLSafe(encryptedBytes);
			return new String(encodedBytes);
		} catch (final Exception e) {
			throw new IllegalStateException("Encryption error!", e);
		}
	}

	private Cipher getCipher(final String algorithm, final int mode) {
		try {
			final Cipher cipher = Cipher.getInstance(algorithm);
			cipher.init(mode, secretKey);
			return cipher;
		} catch (final Exception e) {
			throw new IllegalStateException(
					String.format("Error creating cipher for: %s in mode=%s",
							algorithm, mode), e);
		}
	}

	private Cipher getDecryptCipher() {
		if (decryptCipher == null) {
			decryptCipher = getCipher(algorithm, Cipher.DECRYPT_MODE);
		}
		return decryptCipher;
	}

	private Cipher getEncryptCipher() {
		if (encryptCipher == null) {
			encryptCipher = getCipher(algorithm, Cipher.ENCRYPT_MODE);
		}
		return encryptCipher;
	}

}
