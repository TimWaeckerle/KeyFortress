package keyfortress.domain.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class EncryptionService {

	public static byte[] generateSalt(int length) {
		byte[] salt = new byte[length];
		SecureRandom random = new SecureRandom();
		random.nextBytes(salt);
		return salt;
	}

	public static byte[] encryptPassword(String password, byte[] salt) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.reset();
			digest.update(salt);
			byte[] hashedPassword = digest.digest(password.getBytes());
			return hashedPassword;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
}
