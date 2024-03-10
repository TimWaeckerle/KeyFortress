package keyfortress.domain.entities;

import java.util.List;
import java.util.UUID;

import keyfortress.domain.valueObjects.KeystorePassword;

public class Keystore {
	private final UUID keystoreID;
	private String name;
	private KeystorePassword password;
	private List<KeystoreEntry> KeyEntries;

	public Keystore(String name, KeystorePassword password) {
		this.keystoreID = UUID.randomUUID();
		this.name = name;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public KeystorePassword getPassword() {
		return password;
	}

	public void setPassword(KeystorePassword password) {
		this.password = password;
	}

	public List<KeystoreEntry> getKeyEntries() {
		return KeyEntries;
	}

	public void addKeystoreEntry(KeystoreEntry keyEntries) {
		KeyEntries.add(keyEntries);
	}
}
