package keyfortress.domain.user;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import keyfortress.domain.keystore.Keystore;

public class User {

	private final UUID id;
	private String name;
	private AccountPassword password;
	private List<Keystore> keystores;

	public User() {
		this.id = UUID.randomUUID();
		this.keystores = new ArrayList<>();
	}

	@JsonCreator
	public User(@JsonProperty("name") String name, @JsonProperty("password") AccountPassword password) {
		this.id = UUID.randomUUID();
		this.name = name;
		this.password = password;
		this.keystores = new ArrayList<Keystore>();
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

	public List<Keystore> getKeystores() {
		return keystores;
	}

	public void addKeystores(Keystore keystore) {
		this.keystores.add(keystore);
	}

	public void removeKeystores(Keystore keystore) {
		this.keystores.remove(keystore);
	}
}
