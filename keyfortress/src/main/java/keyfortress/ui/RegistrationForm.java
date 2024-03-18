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
import keyfortress.domain.exceptions.PasswordValidationException;
import keyfortress.domain.repositories.FileSystemUserRepository;
import keyfortress.domain.services.UserService;

public class RegistrationForm extends Application {

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Register");

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		Label usernameLabel = new Label("Benutzername:");
		TextField usernameTextField = new TextField();
		Label passwordLabel = new Label("Passwort:");
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

		Button registerButton = new Button("Register");

		registerButton.setOnAction(e -> {
			createUser(usernameTextField.getText(), passwordTextField.getText());
			primaryStage.close();
		});

		grid.add(registerButton, 0, 2);

		Scene scene = new Scene(grid);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void createUser(String username, String password) {
		UserService userService = new UserService(new FileSystemUserRepository());
		try {
			if (!userService.createUser(username, password)) {
				showAlert("Error", "User already exists. Choose another name");
			} else {
				showAlert("Information", "Account created successfully");
			}
		} catch (PasswordValidationException ex) {
			showAlert("Error", ex.getMessage());
			return;
		}
	}

	private void showAlert(String title, String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
