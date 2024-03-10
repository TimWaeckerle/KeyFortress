package keyfortress.domain.valueObjects;

import java.util.Arrays;

import keyfortress.ErrorMessages;
import keyfortress.domain.exceptions.PasswordValidationException;
import keyfortress.domain.services.EncryptionService;
import keyfortress.domain.services.PasswordValidationService;

public class PasswordEntries implements IPassword {

	private byte[] password;
	private byte[] salt;

	public PasswordEntries(String password) throws PasswordValidationException {
		if (PasswordValidationService.validate(password, 1, false, false)) {
			this.salt = EncryptionService.generateSalt(saltSize);
			this.password = EncryptionService.encryptPassword(password, salt);
		} else {
			throw new PasswordValidationException(ErrorMessages.PasswordEntriesMessage.toString());
		}
	}

	@Override
	public byte[] getPassword() {
		return password;
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
		PasswordEntries other = (PasswordEntries) obj;
		return Arrays.equals(password, other.password) && Arrays.equals(salt, other.salt);
	}
}
