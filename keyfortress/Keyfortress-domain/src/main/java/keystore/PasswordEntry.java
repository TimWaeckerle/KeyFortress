package keystore;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import password.ErrorMessages;
import password.Password;
import password.PasswordRestriction;
import password.PasswordValidationException;

public class PasswordEntry implements Password {

	private final byte[] password;
	private final byte[] salt;
	private final String key;

	public PasswordEntry(String password, PasswordRestriction restriction) throws Exception {
		if (restriction.isValid()) {
			this.salt = generateSalt(saltSize);
			this.key = generateRandomKey();
			this.password = generatePassword(password);
		} else {
			throw new PasswordValidationException(ErrorMessages.PasswordEntriesMessage.getValue());
		}
	}

	private byte[] generatePassword(String password) throws Exception {
		return encryptSymmetrical(password, key).getBytes();
	}

	public String getDecryptedPassword() throws Exception {
		return decryptSymmetrical(new String(password), key);
	}

	private byte[] generateSalt(int length) {
		byte[] salt = new byte[length];
		SecureRandom random = new SecureRandom();
		random.nextBytes(salt);
		return salt;
	}

	private String encryptSymmetrical(String password, String key) throws Exception {
		Cipher cipher = Cipher.getInstance("AES");
		SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte[] encryptedBytes = cipher.doFinal(password.getBytes());
		return Base64.getEncoder().encodeToString(encryptedBytes);
	}

	private String decryptSymmetrical(String encryptedPassword, String key) throws Exception {
		Cipher cipher = Cipher.getInstance("AES");
		SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
		return new String(decryptedBytes);
	}

	private static String generateRandomKey() {
		byte[] randomBytes = new byte[16];
		new SecureRandom().nextBytes(randomBytes);
		return Base64.getEncoder().encodeToString(randomBytes);
	}

	@Override
	public byte[] getPassword() {
		return password;
	}

	public String getClearPassword() {
		return password.toString();
	}

	@Override
	public String toString() {
		return "PasswordEntries [password=" + Arrays.toString(password) + ", salt=" + Arrays.toString(salt) + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(password);
		result = prime * result + Arrays.hashCode(salt);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PasswordEntry other = (PasswordEntry) obj;
		return Arrays.equals(password, other.password) && Arrays.equals(salt, other.salt);
	}
}
