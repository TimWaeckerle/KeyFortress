package keyfortress;

public enum ErrorMessages {
	
	AccountPasswordMessage("Password validation failed. Please use at least 6 characters, and it must contain a number."),
	PasswordEntriesMessage("Password validation failed. Password can't be empty."),
	KeyStorePasswordMessage("Password validation failed. Please use at least 8 characters, and it must contain a number and a special character");

	private final String value;

	ErrorMessages(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
