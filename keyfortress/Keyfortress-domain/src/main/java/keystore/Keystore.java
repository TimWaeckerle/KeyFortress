package keystore;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import password.ErrorMessages;

public class Keystore {
	private final UUID keystoreID;
	private String name;
	private KeystorePassword password;
	private List<KeystoreEntry> keyEntries;
	private UUID userID;

	public Keystore(String name, KeystorePassword password, UUID userID) {
		this.keystoreID = UUID.randomUUID();
		this.name = name;
		this.password = password;
		this.userID = userID;
		keyEntries = new ArrayList<>();
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
		return keyEntries;
	}

	public void addKeystoreEntry(KeystoreEntry entry) throws ObjectAlreadyExistsException {
		if (keyEntries == null) {
			keyEntries = new ArrayList<>();
		}

		for (KeystoreEntry existingEntry : keyEntries) {
			if (existingEntry.getName().equals(entry.getName())) {
				throw new ObjectAlreadyExistsException(ErrorMessages.EntryAlreadyExists.getValue());
			}
		}
		keyEntries.add(entry);
	}

	public UUID getKeystoreID() {
		return keystoreID;
	}

	public void removeKeyEntrie(KeystoreEntry entry) {
		int index = -1;
		for (int i = 0; i < keyEntries.size(); i++) {
			if (keyEntries.get(i).getName().equals(entry.getName())) {
				index = i;
				break;
			}
		}
		if (index != -1) {
			keyEntries.remove(index);
		}
	}

	public UUID getUserID() {
		return userID;
	}

}
