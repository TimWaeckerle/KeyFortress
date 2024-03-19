package keyfortress.domain.services;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import keyfortress.domain.exceptions.PasswordValidationException;
import keyfortress.domain.repositories.FileSystemUserRepository;
import keyfortress.domain.user.AccountPassword;
import keyfortress.domain.user.User;

public class UserService {

	private static User loggedInUser;

	private final FileSystemUserRepository userRepository;

	public UserService(FileSystemUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void saveUser(User user) {
		userRepository.saveUser(user);
	}

	public void connectUserToKeystore(User user, UUID keystoreID) {
		user.addKeystores(keystoreID);
		userRepository.saveUser(user);
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User getUserById(UUID userId) {
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

	public List<UUID> getAllKeystoresForUser(UUID userId) {
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

	public boolean authenticateUser(String username, String password) throws PasswordValidationException {
		User user = userRepository.findUserByUsername(username);

		if (user != null) {
			byte[] storedPasswordHash = user.getPassword().getPassword();
			byte[] storedSalt = user.getPassword().getSalt();

			byte[] enteredPasswordHash = EncryptionService.encryptPasswordPermanent(password, storedSalt);

			if (Arrays.equals(storedPasswordHash, enteredPasswordHash)) {
				loggedInUser = user;
				return true;
			}
		}
		return false;
	}

	public User getLoggedInUser() {
		return loggedInUser;
	}
}
