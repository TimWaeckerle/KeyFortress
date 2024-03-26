package user;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import password.ErrorMessages;
import password.Password;
import password.PasswordRestriction;
import password.PasswordValidationException;

public class AccountPassword implements Password {

	private final byte[] password;
	private final byte[] salt;

	public AccountPassword(String password, PasswordRestriction restriction) throws PasswordValidationException {
		if (restriction.isValid()) {
			this.salt = generateSalt(saltSize);
			this.password = encryptPasswordPermanent(password, salt);
		} else {
			throw new PasswordValidationException(ErrorMessages.AccountPasswordMessage.getValue());
		}
	}

	public AccountPassword(String password, byte[] salt) {
		this.salt = salt;
		this.password = encryptPasswordPermanent(password, salt);
	}

	@Override
	public byte[] getPassword() {
		return password;
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
		AccountPassword other = (AccountPassword) obj;
		return Arrays.equals(password, other.password) && Arrays.equals(salt, other.salt);
	}

	@Override
	public String toString() {
		return "AccountPassword [password=" + Arrays.toString(password) + ", salt=" + Arrays.toString(salt) + "]";
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
