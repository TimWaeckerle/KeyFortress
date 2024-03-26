package password.strength;

import java.util.ArrayList;
import java.util.List;

import keystore.Keystore;
import keystore.KeystoreEntry;

public class PasswordStrengthEvaluator {
	private List<PasswordEvaluationStrategy> evaluationStrategies;

	public PasswordStrengthEvaluator(List<PasswordEvaluationStrategy> evaluationStrategies) {
		this.evaluationStrategies = evaluationStrategies;
	}

	public int evaluateKeystorePasswordStrength(Keystore keystore) {
		if (keystore == null) {
			return 0;
		}

		List<String> decryptPasswords = getAllPasswordsFromKeystore(keystore);
		if (decryptPasswords == null || decryptPasswords.isEmpty()) {
			return 0;
		}

		int evaluation = 0;
		for (String password : decryptPasswords) {
			for (PasswordEvaluationStrategy strategy : evaluationStrategies) {
				evaluation += strategy.getEvaluationForPassword(password);
			}
		}
		return evaluation / decryptPasswords.size();
	}

	private List<String> getAllPasswordsFromKeystore(Keystore keystore) {
		List<String> encryptedPasswords = new ArrayList<>();
		List<KeystoreEntry> entries = keystore.getKeyEntries();
		if (entries != null) {
			for (KeystoreEntry keystoreEntry : entries) {
				try {
					String password = keystoreEntry.getPassword().getDecryptedPassword();
					encryptedPasswords.add(password);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return encryptedPasswords;
	}
}
