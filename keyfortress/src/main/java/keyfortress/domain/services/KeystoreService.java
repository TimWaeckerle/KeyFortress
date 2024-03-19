package keyfortress.domain.services;

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
}
