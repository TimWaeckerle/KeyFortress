package keyfortress.domain.keystore;

import java.util.List;
import java.util.UUID;

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

	public UUID getKeystoreID() {
		return keystoreID;
	}

	public void addKeyEntrie(KeystoreEntry keyEntrie) {
		KeyEntries.add(keyEntrie);
	}
	
	public void removeKeyEntrie(KeystoreEntry keyEntrie) {
		KeyEntries.remove(keyEntrie);
	}
}
