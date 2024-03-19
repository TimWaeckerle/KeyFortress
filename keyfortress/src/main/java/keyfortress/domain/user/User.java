package keyfortress.domain.user;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

	private final UUID id;
	private String name;
	private AccountPassword password;
	private List<UUID> keystores;

	public User() {
		this.id = UUID.randomUUID();
		this.keystores = new ArrayList<>();
	}

	public User(String name, AccountPassword password) {
		this.id = UUID.randomUUID();
		this.name = name;
		this.password = password;
		this.keystores = new ArrayList<UUID>();
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

	@JsonProperty("id")
	public UUID getId() {
		return id;
	}

	public List<UUID> getKeystores() {
		return keystores;
	}

	public void addKeystores(UUID id) {
		this.keystores.add(id);
	}

	public void removeKeystores(UUID id) {
		this.keystores.remove(id);
	}
}
