package keyfortress.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import keyfortress.domain.repositories.FileSystemUserRepository;
import keyfortress.domain.services.UserService;
import keyfortress.domain.user.User;

public class LoginForm extends Application {

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
		col1.setPercentWidth(40);
		ColumnConstraints col2 = new ColumnConstraints();
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
			UserService userService = new UserService(new FileSystemUserRepository());

			if (userService.authenticateUser(username, password)) {
				loginUser(userService.getUserByName(username));
			} else {
				showAlert("Error on Login", "Wrong Username or Password. Please try again.");
			}
		});

		registerButton.setOnAction(e -> {
			startRegisterUi();
		});

		grid.add(loginButton, 0, 2);
		grid.add(registerButton, 1, 2);

		Scene scene = new Scene(grid);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void loginUser(User user) {
		Stage loggedInView = new Stage();
		loggedInView.setTitle(user.getName());
		KeyStoreOverviewForm keystoreForm = new KeyStoreOverviewForm();
		keystoreForm.start(loggedInView);
		loggedInView.show();
	}

	private void startRegisterUi() {
		Stage registrationStage = new Stage();
		registrationStage.setTitle("Registration Form");
		RegistrationForm registrationForm = new RegistrationForm();
		registrationForm.start(registrationStage);
		registrationStage.show();
	}

	private void showAlert(String title, String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
