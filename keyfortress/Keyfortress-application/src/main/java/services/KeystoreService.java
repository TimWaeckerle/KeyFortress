package services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import keystore.Keystore;
import keystore.KeystoreEntry;
import keystore.KeystorePassword;
import keystore.KeystoreRepository;
import keystore.ObjectAlreadyExistsException;
import keystore.PasswordEntry;
import password.PasswordRestriction;
import user.User;

public class KeystoreService {
	private final KeystoreRepository keystoreRepository;

	public KeystoreService(KeystoreRepository keystoreRepository) {
		this.keystoreRepository = keystoreRepository;
	}

	public List<Keystore> getAllKeystores() {
		return keystoreRepository.findAll();
	}

	public Keystore getKeystoreByID(UUID keystoreID) {
		return keystoreRepository.findKeystoreByID(keystoreID);
	}

	public Keystore createKeystore(String name, String password, User user) throws Exception {
		if (name.isEmpty() || password.isEmpty() || user.equals(null)) {
			throw new Exception("Name and Password can t be empty.");
		}
		if (user.equals(null)) {
			throw new Exception("Couldn t bind Keystore to User. User seems not to be logged in.");
		}

		PasswordRestriction restriction = new PasswordRestriction(password, 8, true, true);
		Keystore keystore = new Keystore(name, new KeystorePassword(password, restriction), user.getId());

		if (keystoreRepository.findKeystoreByID(keystore.getKeystoreID()) != null) {
			throw new Exception();
		}
		keystoreRepository.saveKeystore(keystore);
		return keystore;
	}

	public void deleteKeystore(Keystore keystore) {
		keystoreRepository.deleteKeystore(keystore.getKeystoreID());
	}

	public boolean verifyPasswordForKeystore(Keystore keystore, String password) {
		if (keystore != null) {
			KeystorePassword enteredPassword = new KeystorePassword(password, keystore.getPassword().getSalt());
			return keystore.getPassword().equals(enteredPassword);
		}
		return false;
	}

	public KeystoreEntry createKeystoreEntry(String name, String password) throws Exception {
		if (name.isEmpty() || password.isEmpty()) {
			throw new Exception("Name or Password can't be empty.");
		}
		PasswordRestriction restriction = new PasswordRestriction(password, 1, false, false);
		return new KeystoreEntry(name, new PasswordEntry(password, restriction));
	}

	private void saveKeystore(Keystore keystore) {
		keystoreRepository.saveKeystore(keystore);
	}

	public void addKeystoreEntry(Keystore keystore, KeystoreEntry keystoreEntry) throws ObjectAlreadyExistsException {
		keystore.addKeystoreEntry(keystoreEntry);
		saveKeystore(keystore);
	}

	public void addKeystoreEntry(UUID keystoreID, KeystoreEntry keystoreEntry) throws ObjectAlreadyExistsException {
		Keystore keystore = getKeystoreByID(keystoreID);
		keystore.addKeystoreEntry(keystoreEntry);
		saveKeystore(keystore);
	}

	public void removeKeystoreEntry(Keystore keystore, KeystoreEntry keystoreEntry) {
		keystore.removeKeyEntrie(keystoreEntry);
		saveKeystore(keystore);
	}

	public void updateKeystoreEntry(UUID keystoreID, KeystoreEntry keystoreEntry, String newPassword, String newName)
			throws Exception {
		Keystore keystore = keystoreRepository.findKeystoreByID(keystoreID);
		keystore.removeKeyEntrie(keystoreEntry);
		keystore.addKeystoreEntry(createKeystoreEntry(newName, newPassword));
		keystoreRepository.saveKeystore(keystore);
	}

	public List<UUID> getAllKeystoresForUser(User user) {
		if (user.equals(null)) {
			return null;
		}
		List<UUID> keystoreIDs = new ArrayList<>();
		List<Keystore> allKeystores = keystoreRepository.findAll();

		for (Keystore keystore : allKeystores) {
			if (keystore.getUserID().equals(user.getId())) {
				keystoreIDs.add(keystore.getKeystoreID());
			}
		}
		return keystoreIDs;
	}
}
