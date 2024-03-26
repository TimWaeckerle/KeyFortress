package password.strength;

public class LengthPasswordEvaluationStrategy implements PasswordEvaluationStrategy {
	@Override
	public int getEvaluationForPassword(String password) {
		int length = password.length();

		if (length >= 10) {
			return 3;
		} else if (length >= 8) {
			return 2;
		} else if (length >= 6) {
			return 1;
		} else {
			return 0;
		}
	}
}
