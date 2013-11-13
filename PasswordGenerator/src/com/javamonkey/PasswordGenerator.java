package com.javamonkey;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class PasswordGenerator {

	private static final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();

	public String generatePasswordForSeed(String seed, int hourToUse) {
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		int day = gregorianCalendar.get(Calendar.DAY_OF_MONTH);
		int month = gregorianCalendar.get(Calendar.MONTH);
		int hour = hourToUse == -1 ? gregorianCalendar.get(Calendar.HOUR_OF_DAY) : hourToUse;

		String encrypted = getMd5Hash(seed + day + month + hour);

		return encrypted.substring(0, 8);
	}

	private String getMd5Hash(String input) {
		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}

		messageDigest.reset();
		messageDigest.update(input.getBytes(Charset.forName("UTF8")));
		final byte[] digest = messageDigest.digest();

		return toHex(digest).toUpperCase();
	}

	private static String toHex(byte[] data) {
		char[] chars = new char[data.length * 2];
		for (int i = 0; i < data.length; i++) {
			chars[i * 2] = HEX_DIGITS[(data[i] >> 4) & 0xf];
			chars[i * 2 + 1] = HEX_DIGITS[data[i] & 0xf];
		}
		return new String(chars);
	}
}
