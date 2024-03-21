package keyfortress.plugins.ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import keyfortress.application.services.UserService;
import keyfortress.domain.password.PasswordValidationException;
import keyfortress.domain.user.User;
import keyfortress.plugins.persistence.FileSystemUserRepository;

public class LoginForm extends KeyFortressUI {

	UserService userService;

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Login");

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		Label usernameLabel = new Label("Username:");
		TextField usernameTextField = new TextField();
		Label passwordLabel = new Label("Password:");
		PasswordField passwordTextField = new PasswordField();

		ColumnConstraints col1 = new ColumnConstraints();
		ColumnConstraints col2 = new ColumnConstraints();
		col1.setPercentWidth(40);
		col2.setPercentWidth(30);
		grid.getColumnConstraints().addAll(col1, col2);
		grid.add(usernameLabel, 0, 0);
		grid.add(usernameTextField, 1, 0);
		grid.add(passwordLabel, 0, 1);
		grid.add(passwordTextField, 1, 1);

		Button loginButton = new Button("Login");
		Button registerButton = new Button("Register");

		loginButton.setOnAction(e -> {
			String username = usernameTextField.getText();
			String password = passwordTextField.getText();
			userService = new UserService(new FileSystemUserRepository());

			try {
				if (userService.authenticateUser(username, password)) {
					loginUser(userService.getUserByName(username));
					primaryStage.close();
				} else {
					showAlert("Error on Login", "Wrong Username or Password. Please try again."
							+ " You also can create an account via the 'Register' button.");
				}
			} catch (PasswordValidationException e1) {
				showAlert("Error on Login", "Wrong Username or Password. Please try again."
						+ " You also can create an account via the 'Register' button.");
			}
		});

		registerButton.setOnAction(e -> {
			startRegisterUi();
		});

		grid.add(loginButton, 0, 2);
		grid.add(registerButton, 1, 2);

		grid.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				loginButton.fire();
			}
		});

		Scene scene = new Scene(grid);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void loginUser(User user) {
		Stage keystoreOverview = new Stage();
		keystoreOverview.setTitle(user.getName());
		KeystoreOverviewForm keystoreOverviewForm = new KeystoreOverviewForm();
		keystoreOverviewForm.start(keystoreOverview);
		keystoreOverview.show();
	}

	private void startRegisterUi() {
		Stage registrationStage = new Stage();
		registrationStage.setTitle("Registration");
		RegistrationForm registrationForm = new RegistrationForm();
		registrationForm.start(registrationStage);
		registrationStage.show();
	}
}
