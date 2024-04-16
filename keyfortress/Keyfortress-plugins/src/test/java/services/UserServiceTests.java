package services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import password.PasswordRestriction;
import user.AccountPassword;
import user.User;
import user.UserRepository;

public class UserServiceTests {

	@Mock
	private PasswordRestriction mockPasswordRestriction;

	@Mock
	private UserRepository mockRepository;

	private UserService userService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		userService = new UserService(mockRepository);
		when(mockPasswordRestriction.isValid()).thenReturn(true);
	}

	@Test
	void testCreateUser() throws Exception {
		String username = "testuser";
		String password = "testpassword1";

		when(mockRepository.findUserByUsername(username)).thenReturn(null);

		userService.createUser(username, password);

		verify(mockRepository, times(1)).saveUser(any(User.class));
	}

	@Test
	void testCreateUser_UsernameAlreadyExists() throws Exception {
		String username = "existinguser";
		String password = "testpassword1";
		User existingUser = new User(username, new AccountPassword(password, mockPasswordRestriction));

		when(mockRepository.findUserByUsername(username)).thenReturn(existingUser);

		assertThrows(Exception.class, () -> userService.createUser(username, password));
	}

	@Test
	void testAuthenticateUser_Success() throws Exception {
		String username = "testuser";
		String password = "testpassword1";
		User existingUser = new User(username, new AccountPassword(password, mockPasswordRestriction));

		when(mockRepository.findUserByUsername(username)).thenReturn(existingUser);

		boolean isAuthenticated = userService.authenticateUser(username, password);

		assertTrue(isAuthenticated);
	}

	@Test
	void testAuthenticateUser_Failure() throws Exception {
		String username = "testuser";
		String password = "testpassword1";
		String wrongPassword = "wrongpassword";
		User existingUser = new User(username, new AccountPassword(password, mockPasswordRestriction));

		when(mockRepository.findUserByUsername(username)).thenReturn(existingUser);

		boolean isAuthenticated = userService.authenticateUser(username, wrongPassword);
		assertFalse(isAuthenticated);
	}
}
