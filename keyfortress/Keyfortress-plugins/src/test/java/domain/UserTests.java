package domain;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import password.PasswordRestriction;
import password.PasswordValidationException;
import user.AccountPassword;
import user.User;

public class UserTests {
	@Mock
	PasswordRestriction mockPasswordRestriction;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		when(mockPasswordRestriction.isValid()).thenReturn(true);
	}

	@Test
	void testConstructorAndGetters() throws PasswordValidationException {
		String name = "Alice";
		String passwordString = "secretpassword";
		AccountPassword password = new AccountPassword(passwordString, mockPasswordRestriction);
		User user = new User(name, password);

		assertEquals(name, user.getName());
		assertEquals(password, user.getPassword());
	}

	@Test
	void testSetters() throws PasswordValidationException {
		String name = "Bob";
		String passwordString = "anotherpassword";
		AccountPassword password = new AccountPassword(passwordString, mockPasswordRestriction);
		User user = new User();

		user.setName(name);
		user.setPassword(password);

		assertEquals(name, user.getName());
		assertEquals(password, user.getPassword());
	}

}
