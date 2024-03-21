package keyfortress.plugins.persistence;

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

import keyfortress.domain.user.User;
import keyfortress.domain.user.UserRepository;

public class FileSystemUserRepository implements UserRepository {

	private final String filePath = "userdata.json";
	private final Gson gson;

	public FileSystemUserRepository() {
		this.gson = new Gson();
	}

	@Override
	public void saveUser(User user) {
		List<User> users = findAll();
		boolean userExists = false;

		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getId().equals(user.getId())) {
				users.set(i, user);
				userExists = true;
				break;
			}
		}

		if (!userExists) {
			users.add(user);
		}

		writeToFile(users);
	}

	public List<User> findAll() {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				file.createNewFile();
				return new ArrayList<>();
			}
			FileReader reader = new FileReader(filePath);
			Type userListType = new TypeToken<ArrayList<User>>() {
			}.getType();
			List<User> users = gson.fromJson(reader, userListType);
			reader.close();
			return users != null ? users : new ArrayList<>();
		} catch (IOException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	private void writeToFile(List<User> users) {
		try {
			FileWriter writer = new FileWriter(filePath);
			gson.toJson(users, writer);
			writer.close();
		} catch (IOException e) {
			// TODO
		}
	}

	@Override
	public User findUserByID(UUID userId) {
		List<User> users = findAll();
		for (User user : users) {
			if (user.getId().equals(userId)) {
				return user;
			}
		}
		return null;
	}

	@Override
	public void update(User user) {
		List<User> users = findAll();
		users.removeIf(u -> u.getId().equals(user.getId()));
		users.add(user);
		writeToFile(users);
	}

	@Override
	public void delete(String userId) {
		List<User> users = findAll();
		users.removeIf(u -> u.getId().toString().equals(userId));
		writeToFile(users);
	}

	public User findUserByUsername(String username) {
		List<User> users = findAll();
		for (User user : users) {
			if (user.getName().equals(username)) {
				return user;
			}
		}
		return null;
	}
}
