package keyfortress.domain.repositories;

import java.util.UUID;

import keyfortress.domain.keystore.Keystore;
import keyfortress.domain.keystore.KeystoreEntry;

public interface KeystoreRepository {
	public Keystore findKeystoreByID(UUID keystoreID);

	public void saveKeystore(Keystore keystore);

	public void addKeystoreEntry(KeystoreEntry entry);

	public void removeKeystoreEntry(KeystoreEntry entry);

	public void deleteKeystore(UUID keystoreID);
}
