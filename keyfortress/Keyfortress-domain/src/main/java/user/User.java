package user;

import java.util.UUID;

public class User {

	private final UUID id;
	private String name;
	private AccountPassword password;

	public User() {
		this.id = UUID.randomUUID();
	}

	public User(String name, AccountPassword password) {
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

	public AccountPassword getPassword() {
		return password;
	}

	public void setPassword(AccountPassword password) {
		this.password = password;
	}

	public UUID getId() {
		return id;
	}
}
