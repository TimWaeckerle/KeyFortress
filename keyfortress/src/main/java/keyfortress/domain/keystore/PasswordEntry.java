package keyfortress.domain.keystore;

import java.util.Arrays;

import keyfortress.ErrorMessages;
import keyfortress.domain.exceptions.PasswordValidationException;
import keyfortress.domain.services.EncryptionService;
import keyfortress.domain.services.PasswordValidationService;

public class PasswordEntry implements IPassword {

	private byte[] password;
	private byte[] salt;
	private String key;

	public PasswordEntry(String password) throws Exception {
		if (PasswordValidationService.validate(password, 1, false, false)) {
			this.salt = EncryptionService.generateSalt(saltSize);
			generatePassword(password);
		} else {
			throw new PasswordValidationException(ErrorMessages.PasswordEntriesMessage.getValue());
		}
	}

	private void generatePassword(String password) throws Exception {
		key = EncryptionService.generateRandomKey();
		this.password = EncryptionService.encryptSymmetrical(password, key).getBytes();
	}

	public String getDecryptedPassword() throws Exception {
		return EncryptionService.decryptSymmetrical(new String(password), key);
	}

	@Override
	public byte[] getPassword() {
		return password;
	}

	public void setKey(String key) {
		this.key = key;
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
