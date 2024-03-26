package keystore;

import java.util.List;
import java.util.UUID;

public interface KeystoreRepository {
	public Keystore findKeystoreByID(UUID keystoreID);

	public void saveKeystore(Keystore keystore);

	public void addKeystoreEntry(KeystoreEntry entry);

	public void removeKeystoreEntry(KeystoreEntry entry);

	public void deleteKeystore(UUID keystoreID);

	public List<Keystore> findAll();
}
