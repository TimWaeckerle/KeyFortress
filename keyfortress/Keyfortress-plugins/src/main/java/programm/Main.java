package programm;

import ui.LoginForm;

public class Main {

	public static void main(String[] args) {
		LoginForm login = new LoginForm();
		login.launch(LoginForm.class, args);
	}
}
