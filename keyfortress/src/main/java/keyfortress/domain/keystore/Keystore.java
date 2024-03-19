package keyfortress.domain.keystore;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Keystore {
	private final UUID keystoreID;
	private String name;
	private KeystorePassword password;
	private List<KeystoreEntry> keyEntry;

	public Keystore(String name, KeystorePassword password) {
		this.keystoreID = UUID.randomUUID();
		this.name = name;
		this.password = password;
		keyEntry = new ArrayList<>();
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
		return keyEntry;
	}

	public void addKeystoreEntry(KeystoreEntry entry) {
		if (keyEntry == null) {
			keyEntry = new ArrayList<>();
		}
		keyEntry.add(entry);
	}

	public UUID getKeystoreID() {
		return keystoreID;
	}

	public void removeKeyEntrie(KeystoreEntry entry) {
		keyEntry.remove(entry);
	}
}
