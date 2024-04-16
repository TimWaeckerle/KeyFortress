package domain;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import password.PasswordRestriction;

public class PasswordRestrictionTests {

	private static int minLength;

	@BeforeAll
	static void setup() {
		minLength = 8;
	}

	@Test
	void testValidPasswordLength() {
		String password = "password";
		boolean isValid = PasswordRestriction.checkPasswordLength(password, minLength);
		assertTrue(isValid, "Password length should be valid");
	}

	@Test
	void testInvalidPasswordLength() {
		String password = "pass";
		boolean isValid = PasswordRestriction.checkPasswordLength(password, minLength);
		assertFalse(isValid, "Password length should be invalid");
	}

	@Test
	void testPasswordContainsNumber() {
		String password = "password1";
		boolean containsNumber = PasswordRestriction.checkForNumber(password);
		assertTrue(containsNumber, "Password should contain a number");
	}

	@Test
	void testPasswordDoesNotContainNumber() {
		String password = "password";
		boolean containsNumber = PasswordRestriction.checkForNumber(password);
		assertFalse(containsNumber, "Password should not contain a number");
	}

	@Test
	void testPasswordContainsSpecialChars() {
		String password = "password!";
		boolean containsSpecialChars = PasswordRestriction.checkForSpecialChars(password);
		assertTrue(containsSpecialChars, "Password should contain special characters");
	}

	@Test
	void testPasswordDoesNotContainSpecialChars() {
		String password = "password";
		boolean containsSpecialChars = PasswordRestriction.checkForSpecialChars(password);
		assertFalse(containsSpecialChars, "Password should not contain special characters");
	}
}
