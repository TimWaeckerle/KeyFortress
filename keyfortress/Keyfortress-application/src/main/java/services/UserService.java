package services;

import java.util.List;
import java.util.UUID;

import password.PasswordRestriction;
import password.PasswordValidationException;
import user.AccountPassword;
import user.User;
import user.UserRepository;

public class UserService {

	private static User loggedInUser;

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void saveUser(User user) {
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

	public void createUser(String username, String password) throws Exception {
		if (username.isEmpty() || password.isEmpty()) {
			throw new Exception("Password or Name can't be empty.");
		}

		if (userRepository.findUserByUsername(username) != null) {
			throw new Exception("Username already exists. Choose another one.");
		}

		PasswordRestriction restriction = new PasswordRestriction(password, 6, true, false);
		User newUser = new User(username, new AccountPassword(password, restriction));
		userRepository.saveUser(newUser);
	}

	public boolean authenticateUser(String username, String password) throws PasswordValidationException {
		User user = userRepository.findUserByUsername(username);

		if (user != null) {
			AccountPassword newAccountPassword = new AccountPassword(password, user.getPassword().getSalt());
			if (user.getPassword().equals(newAccountPassword)) {
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
