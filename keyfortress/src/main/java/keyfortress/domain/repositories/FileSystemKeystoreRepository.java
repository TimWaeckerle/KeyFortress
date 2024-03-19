package keyfortress.domain.repositories;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import keyfortress.domain.keystore.Keystore;
import keyfortress.domain.keystore.KeystoreEntry;

public class FileSystemKeystoreRepository implements KeystoreRepository {

	private final String filePath = "keystoredata.json";
	private final Gson gson;

	public FileSystemKeystoreRepository() {
		this.gson = new Gson();
	}

	@Override
	public Keystore findKeystoreByID(UUID keystoreID) {
		List<Keystore> keystores = findAll();
		for (Keystore keystore : keystores) {
			if (keystore.getKeystoreID().equals(keystoreID)) {
				return null;
			}
		}
		return null;
	}

	@Override
	public void saveKeystore(Keystore keystore) {
		List<Keystore> keystores = findAll();
		boolean keystoreExists = false;

		for (int i = 0; i < keystores.size(); i++) {
			if (keystores.get(i).getKeystoreID().equals(keystore.getKeystoreID())) {
				keystores.set(i, keystore);
				keystoreExists = true;
				break;
			}
		}

		if (!keystoreExists) {
			keystores.add(keystore);
		}

		writeToFile(keystores);
	}

	@Override
	public void addKeystoreEntry(KeystoreEntry entry) {
	}

	@Override
	public void removeKeystoreEntry(KeystoreEntry entry) {
	}

	public List<Keystore> findAll() {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				file.createNewFile();
				return new ArrayList<>();
			}
			FileReader reader = new FileReader(filePath);
			Type keystoreListType = new TypeToken<ArrayList<Keystore>>() {
			}.getType();
			List<Keystore> keystores = gson.fromJson(reader, keystoreListType);
			reader.close();
			return keystores != null ? keystores : new ArrayList<>();
		} catch (IOException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	private void writeToFile(List<Keystore> keystores) {
		try {
			FileWriter writer = new FileWriter(filePath);
			gson.toJson(keystores, writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
