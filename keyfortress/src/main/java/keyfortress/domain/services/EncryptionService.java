package keyfortress.domain.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionService {

	public static byte[] generateSalt(int length) {
		byte[] salt = new byte[length];
		SecureRandom random = new SecureRandom();
		random.nextBytes(salt);
		return salt;
	}

	public static byte[] encryptPasswordPermanent(String password, byte[] salt) {
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

	public static String encryptSymmetrical(String password, String key) throws Exception {
		Cipher cipher = Cipher.getInstance("AES");
		SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte[] encryptedBytes = cipher.doFinal(password.getBytes());
		return Base64.getEncoder().encodeToString(encryptedBytes);
	}

	public static String decryptSymmetrical(String encryptedPassword, String key) throws Exception {
		Cipher cipher = Cipher.getInstance("AES");
		SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
		return new String(decryptedBytes);
	}

	public static String generateRandomKey() {
		byte[] randomBytes = new byte[16];
		new SecureRandom().nextBytes(randomBytes);
		return Base64.getEncoder().encodeToString(randomBytes);
	}
}
