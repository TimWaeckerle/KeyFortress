package keystore;

import java.util.Objects;

public class KeystoreEntry {

	private final String name;
	private final PasswordEntry password;

	public KeystoreEntry(String name, PasswordEntry password) {
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
