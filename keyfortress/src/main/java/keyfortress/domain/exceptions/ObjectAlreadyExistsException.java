package keyfortress.domain.exceptions;

public class ObjectAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	public ObjectAlreadyExistsException(String message) {
		super(message);
	}
}
