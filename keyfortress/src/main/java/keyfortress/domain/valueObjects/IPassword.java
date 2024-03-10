package keyfortress.domain.valueObjects;

public interface IPassword {
	int saltSize = 10;

	public byte[] getPassword();
}
