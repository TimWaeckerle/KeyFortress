package keyfortress.domain.services;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import keyfortress.domain.keystore.Keystore;
import keyfortress.domain.repositories.FileSystemKeystoreRepository;

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

	public boolean createKeystore(Keystore keystore) {
		if (keystoreRepository.findKeystoreByID(keystore.getKeystoreID()) != null) {
			return false;
		}
		keystoreRepository.saveKeystore(keystore);
		return true;
	}

	public boolean verifyPasswordForKeystore(Keystore keystore, String password) {
		if (keystore != null) {
			byte[] keystorePassword = keystore.getPassword().getPassword();
			byte[] keystorePasswordSalt = keystore.getPassword().getSalt();
			byte[] encryptedEnteredPassword = EncryptionService.encryptPassword(password, keystorePasswordSalt);
			return Arrays.equals(keystorePassword, encryptedEnteredPassword);
		}
		return false;
	}
}
