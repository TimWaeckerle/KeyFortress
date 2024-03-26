package password;

public class PasswordRestriction {

	private boolean isValid;

	public PasswordRestriction(String password, int minLength, boolean checkNumber, boolean checkSpecialChars) {
		isValid = checkPasswordLength(password, minLength);
		if (checkNumber) {
			isValid = isValid && checkForNumber(password);
		}
		if (checkSpecialChars) {
			isValid = isValid && checkForSpecialChars(password);
		}
	}

	public static boolean checkPasswordLength(String password, int minLength) {
		return password.length() >= minLength;
	}

	public static boolean checkForNumber(String password) {
		for (char ch : password.toCharArray()) {
			if (Character.isDigit(ch)) {
				return true;
			}
		}
		return false;
	}

	public static boolean checkForSpecialChars(String password) {
		for (char ch : password.toCharArray()) {
			if (!Character.isLetterOrDigit(ch)) {
				return true;
			}
		}
		return false;
	}

	public boolean isValid() {
		return isValid;
	}
}
