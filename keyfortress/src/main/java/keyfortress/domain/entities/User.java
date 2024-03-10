package keyfortress.domain.entities;

import java.util.List;
import java.util.UUID;

import keyfortress.domain.valueObjects.AccountPassword;
import keyfortress.domain.valueObjects.KeystorePassword;

public class User {

	private final UUID userID;
	private String name;
	private AccountPassword password;
	private List<Keystore> keystores;

	public User(String name, AccountPassword password) {
		this.userID = UUID.randomUUID();
		this.name = name;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AccountPassword getPassword() {
		return password;
	}

	public void setPassword(AccountPassword password) {
		this.password = password;
	}

	public UUID getId() {
		return userID;
	}

	public List<Keystore> getKeystores() {
		return keystores;
	}

	public void setKeystores(List<Keystore> keystores) {
		this.keystores = keystores;
	}
}
