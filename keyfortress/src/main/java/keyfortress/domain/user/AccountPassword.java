package keyfortress.domain.user;

import java.util.Arrays;

import keyfortress.domain.exceptions.ErrorMessages;
import keyfortress.domain.exceptions.PasswordValidationException;
import keyfortress.domain.keystore.IPassword;
import keyfortress.domain.services.EncryptionService;
import keyfortress.domain.services.PasswordValidationService;

public class AccountPassword implements IPassword {

	private final byte[] password;
	private final byte[] salt;

	public AccountPassword(String password) throws PasswordValidationException {
		if (PasswordValidationService.validate(password, 6, true, false)) {
			this.salt = EncryptionService.generateSalt(saltSize);
			this.password = EncryptionService.encryptPasswordPermanent(password, salt);
		} else {
			throw new PasswordValidationException(ErrorMessages.AccountPasswordMessage.getValue());
		}
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

	public byte[] getSalt() {
		return salt;
	}
}
