package keyfortress.domain.keystore;

public interface IPassword {
	int saltSize = 10;

	public byte[] getPassword();
}
