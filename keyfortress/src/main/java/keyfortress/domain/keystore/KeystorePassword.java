package keyfortress.domain.keystore;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import keyfortress.domain.password.ErrorMessages;
import keyfortress.domain.password.Password;
import keyfortress.domain.password.PasswordRestriction;
import keyfortress.domain.password.PasswordValidationException;

public final class KeystorePassword implements Password {

	private byte[] password;
	private byte[] salt;

	public KeystorePassword(String password, PasswordRestriction restriction) throws PasswordValidationException {
		if (restriction.isValid()) {
			this.salt = generateSalt(saltSize);
			this.password = encryptPasswordPermanent(password, salt);
		}  else {
			throw new PasswordValidationException(ErrorMessages.KeyStorePasswordMessage.getValue());
		}
	}
	
	public KeystorePassword(String password, byte[] salt) {
		this.salt = salt;
		this.password = encryptPasswordPermanent(password, salt);
	}

	@Override
	public byte[] getPassword() {
		return password;
	}

	@Override
	public String toString() {
		return "KeystorePassword [password=" + Arrays.toString(password) + ", salt=" + Arrays.toString(salt) + "]";
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
		KeystorePassword other = (KeystorePassword) obj;
		return Arrays.equals(password, other.password) && Arrays.equals(salt, other.salt);
	}
	
	private byte[] generateSalt(int length) {
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

	public byte[] getSalt() {
		return salt;
	}
}