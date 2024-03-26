package password.strength;

public interface PasswordEvaluationStrategy {
	int getEvaluationForPassword(String password);
}
