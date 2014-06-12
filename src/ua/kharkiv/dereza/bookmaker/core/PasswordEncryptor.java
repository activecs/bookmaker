package ua.kharkiv.dereza.bookmaker.core;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

/**
 * Class for encryption of passwords in md5
 * 
 * @author dereza
 * 
 */
public class PasswordEncryptor {

	private static final Logger log = Logger.getLogger(PasswordEncryptor.class);

	private static final String ALGORYTHM = "MD5";
	private static final String CHARSET = "utf-8";

	/**
	 * encrypts passwords in md5
	 * 
	 * @param password
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public String encode(String password) {
		MessageDigest md5;
		String md5StrWithoutZerous;
		StringBuilder md5StrWithZerous = new StringBuilder(32);

		try {
			md5 = MessageDigest.getInstance(ALGORYTHM);
			md5.reset();

			// sends byte-code of string to MessageDigest
			md5.update(password.getBytes(CHARSET));

			// gets MD5-hash without zeros in front
			md5StrWithoutZerous = new BigInteger(1, md5.digest()).toString(16);

			// adds zeros in front if it is necessary
			for (int i = 0, count = 32 - md5StrWithoutZerous.length(); i < count; i++) {
				md5StrWithZerous.append('0');
			}
			
			md5StrWithZerous.append(md5StrWithoutZerous);
			
			log.trace("Input string for encoding --> " + password);
			log.trace("Output encoded string --> " + md5StrWithZerous.toString());
		} catch (NoSuchAlgorithmException e) {
			log.error("Cannot get instance of MessageDigest", e);
		} catch (UnsupportedEncodingException e) {
			log.error("Cannot generate byte-code of string", e);
		}
		return md5StrWithZerous.toString();
	}
}