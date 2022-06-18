package ch.gobothegeek.lofidrox.utils;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.regex.Pattern;

public class GtgPasswordUtils {
	private static final Logger logger = LoggerFactory.getLogger(GtgPasswordUtils.class);

	private static final int isPasswordStrongEnough_PWD_MIN_LENGTH = 10;
	private static final int isPasswordStrongEnough_PWD_MAX_LENGTH = 256;
	private static final String isPasswordStrongEnough_PWD_POLICY = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{" + isPasswordStrongEnough_PWD_MIN_LENGTH + "," + isPasswordStrongEnough_PWD_MAX_LENGTH + "}$";

	// return a new salt to hash password
	public static byte[] generateSalt(int saltLegnth) {
		SecureRandom secPwd;
		byte[] salt;

		// prepare a new salt
		salt = new byte[saltLegnth];
		// Generate a random secure object
		secPwd = new SecureRandom();
		secPwd.nextBytes(salt);
		// return salt
		return salt;
	}

	// return the password hashed
	public static String hashPassword(String pwd, byte[] salt, String algo, int iterations, int keyLength) {
		KeySpec keySpec;
		SecretKeyFactory skFactory;
		byte[] hashPwd;

		// prepare a Password Based Encryption
		keySpec = new PBEKeySpec(pwd.toCharArray(), salt, iterations, keyLength);
		try {
			// choose factory
			skFactory = SecretKeyFactory.getInstance(algo);
			// hash the password
			hashPwd = skFactory.generateSecret(keySpec).getEncoded();
			// done!
			return Hex.encodeHexString(hashPwd);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			logger.error("Unable to use algorithm \"" + algo + "\"", e);
		}
		return null;
	}

	// check that passwords sent by client are identical and meet the requirements
	public static Boolean isPasswordStrongEnough(String pwd, String pwdCtrl) {
		// none may be null or 0 length
		if ((null != pwd) && (0 != pwd.length()) && (null != pwdCtrl) && (0 != pwdCtrl.length())) {
			// must be identical
			if (pwd.equals(pwdCtrl)) {
				// must match policy
				return Pattern.compile(isPasswordStrongEnough_PWD_POLICY).matcher(pwd).matches();
			}
		}
		return false;
	}
}
