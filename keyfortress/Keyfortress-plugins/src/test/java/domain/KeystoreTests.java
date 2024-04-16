package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import keystore.Keystore;
import keystore.KeystoreEntry;
import keystore.KeystorePassword;
import keystore.PasswordEntry;
import password.PasswordRestriction;

public class KeystoreTests {
	@Mock
	PasswordRestriction mockPasswordRestriction;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		when(mockPasswordRestriction.isValid()).thenReturn(true);
	}

	@Test
	void testKeystoreAddEntry() throws Exception {
		String password = "password";

		PasswordEntry mockPasswordEntry = new PasswordEntry(password, mockPasswordRestriction);
		KeystoreEntry mockEntry = new KeystoreEntry("MockEntry", mockPasswordEntry);

		Keystore keystore = new Keystore("Test", new KeystorePassword(password, mockPasswordRestriction),
				UUID.randomUUID());
		keystore.addKeystoreEntry(mockEntry);
		assertTrue(keystore.getKeyEntries().stream().anyMatch(entry -> entry.getName().equals("MockEntry")));
	}

	@Test
	void testKeystoreEntry() throws Exception {
		PasswordEntry passwordEntry = new PasswordEntry("password", mockPasswordRestriction);
		KeystoreEntry entry = new KeystoreEntry("Test", passwordEntry);
		assertEquals("Test", entry.getName());
		assertEquals(passwordEntry, entry.getPassword());
	}

	@Test
	void testKeystorePassword() throws Exception {
		KeystorePassword password = new KeystorePassword("password", mockPasswordRestriction);
		assertNotNull(password.getPassword());
		assertNotNull(password.getSalt());
	}

	@Test
	void testPasswordEntry() throws Exception {
		PasswordEntry entry = new PasswordEntry("password", mockPasswordRestriction);
		assertNotNull(entry.getPassword());
		assertNotNull(entry.getClearPassword());
	}
}
