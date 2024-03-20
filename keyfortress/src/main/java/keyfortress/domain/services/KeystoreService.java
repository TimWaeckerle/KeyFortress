package keyfortress.domain.services;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import keyfortress.domain.exceptions.ObjectAlreadyExistsException;
import keyfortress.domain.keystore.Keystore;
import keyfortress.domain.keystore.KeystoreEntry;
import keyfortress.domain.keystore.KeystorePassword;
import keyfortress.domain.keystore.PasswordEntry;
import keyfortress.domain.repositories.FileSystemKeystoreRepository;
import keyfortress.domain.user.User;

public class KeystoreService {
	private final FileSystemKeystoreRepository keystoreRepository;

	public KeystoreService(FileSystemKeystoreRepository keystoreRepository) {
		this.keystoreRepository = keystoreRepository;
	}

	public List<Keystore> getAllKeystores() {
		return keystoreRepository.findAll();
	}

	public Keystore getKeystoreByID(UUID keystoreID) {
		return keystoreRepository.findKeystoreByID(keystoreID);
	}

	public Keystore createKeystoreAndBindToUser(String name, String password, User user) throws Exception {
		if (name.isEmpty() || password.isEmpty() || user.equals(null)) {
			throw new Exception("Name and Password can t be empty.");
		}
		if (user.equals(null)) {
			throw new Exception("Couldn t bind Keystore to User. User seems not to be logged in.");
		}

		Keystore keystore = new Keystore(name, new KeystorePassword(password));

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
			byte[] keystorePassword = keystore.getPassword().getPassword();
			byte[] keystorePasswordSalt = keystore.getPassword().getSalt();
			byte[] encryptedEnteredPassword = EncryptionService.encryptPasswordPermanent(password,
					keystorePasswordSalt);
			return Arrays.equals(keystorePassword, encryptedEnteredPassword);
		}
		return false;
	}

	public KeystoreEntry createKeystoreEntry(String name, String password) throws Exception {
		if (name.isEmpty() || password.isEmpty()) {
			throw new Exception("Name or Password can't be empty.");
		}
		return new KeystoreEntry(name, new PasswordEntry(password));
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
}
