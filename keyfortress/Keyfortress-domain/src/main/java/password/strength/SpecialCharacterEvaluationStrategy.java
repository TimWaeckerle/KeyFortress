package password.strength;

public class SpecialCharacterEvaluationStrategy implements PasswordEvaluationStrategy {

	@Override
	public int getEvaluationForPassword(String password) {
		if (password.matches(".*[!@#$%^&*()-_=+\\[\\]{};:'\"<>,.?/\\\\].*")) {
			return 3;
		}
		return 0;
	}
}
