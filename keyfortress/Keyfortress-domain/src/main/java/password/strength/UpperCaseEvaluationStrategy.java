package password.strength;

public class UpperCaseEvaluationStrategy implements PasswordEvaluationStrategy {

	@Override
	public int getEvaluationForPassword(String password) {
		if (password.matches(".*[A-Z].*")) {
			return 2;
		}
		return 0;
	}
}
