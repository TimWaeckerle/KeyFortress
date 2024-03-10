package keyfortress.domain.entities;

import java.util.UUID;

import keyfortress.domain.valueObjects.KeystorePassword;

public class User {

	private final UUID id;
	private String name;
	private KeystorePassword password;

	public User(String name, KeystorePassword password) {
		this.id = UUID.randomUUID();
		this.name = name;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public KeystorePassword getPassword() {
		return password;
	}

	public void setPassword(KeystorePassword password) {
		this.password = password;
	}

	public UUID getId() {
		return id;
	}
}
