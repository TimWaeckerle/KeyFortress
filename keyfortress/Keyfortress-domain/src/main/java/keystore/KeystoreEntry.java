package keystore;

import java.util.Objects;

public class KeystoreEntry {

	private final String name;
	private final PasswordEntry password;

	public KeystoreEntry(String name, PasswordEntry password) throws Exception {
		if (password == null || name == null) {
			throw new Exception("Password and name can't be empty");
		}
		this.name = name;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public PasswordEntry getPassword() {
		return password;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		KeystoreEntry that = (KeystoreEntry) obj;
		return Objects.equals(name, that.name) && Objects.equals(password, that.password);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, password);
	}
}
