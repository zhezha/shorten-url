package com.shortenurl.model;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class URLConversion {

	// Digit to character: '0':0, '1':1, ..., '9':9, 'A':10, ..., 'Z':35, 'a':36, ..., 'z':61.
	private static char[] array62Base = new char[62];
	// Character to Digit.
	private static HashMap<Character, Integer> map62Base = new HashMap<Character, Integer>();

	static {
		for (int i = 0; i < 10; i++) {
			array62Base[i] = (char) (48 + i);
			map62Base.put((char) (48 + i), i);
		}
		for (int i = 0; i < 26; i++) {
			array62Base[10 + i] = (char) (65 + i);
			map62Base.put((char) (65 + i), 10 + i);
			array62Base[36 + i] = (char) (97 + i);
			map62Base.put((char) (97 + i), 36 + i);
		}
	}

	
	// Convert id to short URL
	public static String idToShortURL(long value) {

		StringBuilder sb = new StringBuilder();

		while (value >= 62) {
			long quotient = value / 62;
			int remainder = (int) value % 62;
			sb.append(array62Base[remainder]);
			value = quotient;
		}
		sb.append(array62Base[(int) value]);

		return sb.reverse().toString();
	}

	
	// Get MD5 hash of a given string
	public static String getMD5Digest(String string) {

		byte[] byteLongURL = null;
		MessageDigest md = null;
		byte[] bytesDigest = null;
		try {
			byteLongURL = string.getBytes("UTF-8");
			md = MessageDigest.getInstance("MD5");
			bytesDigest = md.digest(byteLongURL);
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < bytesDigest.length; i++) {
			String hex = Integer.toHexString(bytesDigest[i] & 0xff);

			if (hex.length() == 1) {
				hexString.append(0);
			}
			hexString.append(hex);
		}

		return hexString.toString();
	}


	// Convert short URL to id
	public static long shortToID(String shortURL) {

		int result = 0;
		for (int i = 0; i < shortURL.length(); i++) {
			result *= 62;
			result += map62Base.get(shortURL.charAt(i));
		}

		return result;
	}

}
