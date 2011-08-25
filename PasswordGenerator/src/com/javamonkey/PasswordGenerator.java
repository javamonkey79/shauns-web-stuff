package com.javamonkey;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class PasswordGenerator {

	private static final int NUM_DIGITS = 10;
	private static final int NUM_LETTERS = 26;
	private static final int FIRST_LOWER_ASCII = 97;
	private static final int FIRST_UPPER_ASCII = 65;
	private static final int FIRST_NUMERIC_ASCII = 48;

	public String generatePasswordForSeed(String seed) {
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		int day = gregorianCalendar.get(Calendar.DAY_OF_MONTH);
		int month = gregorianCalendar.get(Calendar.MONTH);
		int hour = gregorianCalendar.get(Calendar.HOUR_OF_DAY);

		String encrypted = "";

		char[] chars = seed.toCharArray();
		for (char c : chars) {
			int seedValue = hour + day + month + c;
			int seedGroup = seedValue % 10;

			char movedChar = getMovedCharForSeedGroup(seedValue, seedGroup);

			if (movedChar == c) {
				// wrap the seedgroups
				seedGroup = c > 96 && seedGroup > 6 ? 6 : seedGroup;
				seedGroup = c > 64 && c < 97 && seedGroup > 3 && seedGroup < 7 ? 3
						: seedGroup;
				seedGroup = c < 65 && seedGroup < 4 ? 9 : seedGroup;

				movedChar = getMovedCharForSeedGroup(seedValue, seedGroup);
			}

			encrypted += movedChar;

		}

		return encrypted.substring(0,
				encrypted.length() > 7 ? 8 : encrypted.length());
	}

	private char getMovedCharForSeedGroup(int seedValue, int seedGroup) {
		char movedChar = ' ';

		if (seedGroup < 4) {
			// num
			int movement = seedValue % FIRST_NUMERIC_ASCII;

			movedChar = (char) ((movement % NUM_DIGITS) + FIRST_NUMERIC_ASCII);
		} else if (seedGroup < 7) {
			// upper
			int movement = seedValue % FIRST_UPPER_ASCII;

			movedChar = (char) ((movement % NUM_LETTERS) + FIRST_UPPER_ASCII);
		} else {
			// lower
			int movement = seedValue % 97;

			movedChar = (char) ((movement % NUM_LETTERS) + FIRST_LOWER_ASCII);
		}
		return movedChar;
	}

}
