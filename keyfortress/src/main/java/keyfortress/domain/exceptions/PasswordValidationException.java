package keyfortress.domain.exceptions;

public class PasswordValidationException extends Exception {

	private static final long serialVersionUID = 1L;

	public PasswordValidationException(String message) {
		super(message);
	}
}
