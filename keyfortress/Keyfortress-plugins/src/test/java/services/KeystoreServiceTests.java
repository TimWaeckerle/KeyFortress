package services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import keystore.Keystore;
import keystore.KeystorePassword;
import keystore.KeystoreRepository;
import password.PasswordRestriction;
import password.PasswordValidationException;
import user.AccountPassword;
import user.User;

public class KeystoreServiceTests {
	@Mock
	private KeystoreRepository mockRepository;

	@Mock
	PasswordRestriction mockPasswordRestriction;

	private KeystoreService keystoreService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		keystoreService = new KeystoreService(mockRepository);
		when(mockPasswordRestriction.isValid()).thenReturn(true);
	}

	@Test
	void testGetAllKeystores() throws PasswordValidationException {
		List<Keystore> expectedKeystores = new ArrayList<>();

		expectedKeystores.add(
				new Keystore("Test1", new KeystorePassword("password1", mockPasswordRestriction), UUID.randomUUID()));

		expectedKeystores.add(
				new Keystore("Test2", new KeystorePassword("password2", mockPasswordRestriction), UUID.randomUUID()));

		when(mockRepository.findAll()).thenReturn(expectedKeystores);

		List<Keystore> actualKeystores = keystoreService.getAllKeystores();

		assertEquals(expectedKeystores.size(), actualKeystores.size());
		assertTrue(actualKeystores.containsAll(expectedKeystores));
	}

	@Test
	void testCreateKeystore() throws Exception {
		String name = "TestKeystore";
		String password = "testpassword1!";
		User user = new User("Alice", new AccountPassword(password, mockPasswordRestriction));

		Keystore createdKeystore = keystoreService.createKeystore(name, password, user);

		assertNotNull(createdKeystore);
		assertEquals(name, createdKeystore.getName());
		assertEquals(user.getId(), createdKeystore.getUserID());
	}
}
