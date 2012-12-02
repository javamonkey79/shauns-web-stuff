package com.javamonkey;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class PasswordGenerator {

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
		BigInteger bigInt = new BigInteger(1, digest);
		String hashtext = bigInt.toString(16);

		return hashtext.toUpperCase();
	}
}
