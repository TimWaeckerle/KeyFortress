package keyfortress.domain.services;

import java.util.List;

import keyfortress.domain.exceptions.PasswordValidationException;
import keyfortress.domain.keystore.Keystore;
import keyfortress.domain.repositories.FileSystemUserRepository;
import keyfortress.domain.user.AccountPassword;
import keyfortress.domain.user.User;

public class UserService {

	private final FileSystemUserRepository userRepository;

	public UserService(FileSystemUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void saveUser(User user) {
		userRepository.saveUser(user);
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User getUserById(String userId) {
		return userRepository.findUserByID(userId);
	}

	public User getUserByName(String username) {
		return userRepository.findUserByUsername(username);
	}

	public void updateUser(User user) {
		userRepository.update(user);
	}

	public void deleteUser(String userId) {
		userRepository.delete(userId);
	}

	public List<Keystore> getAllKeystoresForUser(String userId) {
		User user = userRepository.findUserByID(userId);
		if (user != null) {
			return user.getKeystores();
		}
		return null;
	}

	public boolean createUser(String username, String password) throws PasswordValidationException {
		if (userRepository.findUserByUsername(username) != null) {
			return false;
		}
		User newUser = new User(username, new AccountPassword(password));
		userRepository.saveUser(newUser);
		return true;

	}

	public boolean authenticateUser(String username, String password) {
		User user = userRepository.findUserByUsername(username);
		if (user != null && user.getPassword()
				.equals(EncryptionService.encryptPassword(password, user.getPassword().getSalt()))) {
			return true;
		} else {
			return false;
		}
	}
}
