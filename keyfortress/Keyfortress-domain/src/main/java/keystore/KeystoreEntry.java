package keystore;

public class KeystoreEntry {

	private String name;
	private PasswordEntry password;

	public KeystoreEntry(String name, PasswordEntry password) {
		this.name = name;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PasswordEntry getPassword() {
		return password;
	}

	public void setPassword(PasswordEntry password) {
		this.password = password;
	}
}
