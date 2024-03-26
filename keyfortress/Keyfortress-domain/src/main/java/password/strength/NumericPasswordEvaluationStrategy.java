package password.strength;

public class NumericPasswordEvaluationStrategy implements PasswordEvaluationStrategy {
	@Override
	public int getEvaluationForPassword(String password) {
		if (password.matches(".*\\d.*")) {
			return 2;
		} else {
			return 0;
		}
	}
}
