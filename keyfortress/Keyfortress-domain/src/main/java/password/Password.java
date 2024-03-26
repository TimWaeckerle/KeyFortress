package password;

public interface Password {
	int saltSize = 10;

	public byte[] getPassword();
}
