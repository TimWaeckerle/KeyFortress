package keyfortress.domain.repositories;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import keyfortress.domain.user.User;

public class FileSystemUserRepository implements UserRepository {

	private final String filePath = "userdata.json";
	private final ObjectMapper objectMapper;

	public FileSystemUserRepository() {
		this.objectMapper = new ObjectMapper();
	}

	@Override
	public void saveUser(User user) {
		List<User> users = findAll();
		users.add(user);
		writeToFile(users);
	}

	public List<User> findAll() {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				file.createNewFile();
				return new ArrayList<>();
			}
			return objectMapper.readValue(file, new TypeReference<List<User>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	private void writeToFile(List<User> users) {
		try {
			File file = new File(filePath);
			objectMapper.writeValue(file, users);
		} catch (IOException e) {
			// TODO
		}
	}

	@Override
	public User findUserByID(String userId) {

		List<User> users = findAll();
		for (User user : users) {
			if (user.getId().toString().equals(userId)) {
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