package keyfortress.ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import keyfortress.domain.exceptions.PasswordValidationException;
import keyfortress.domain.repositories.FileSystemUserRepository;
import keyfortress.domain.services.UserService;

public class RegistrationForm extends KeyFortressUI {

	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
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
		});

		grid.add(registerButton, 0, 2);

		grid.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				registerButton.fire();
			}
		});

		Scene scene = new Scene(grid);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void createUser(String username, String password) {
		UserService userService = new UserService(new FileSystemUserRepository());
		try {
			userService.createUser(username, password);
			showAlert("Information", "Account created successfully");
			primaryStage.close();
		} catch (PasswordValidationException ex) {
			showAlert("Error", ex.getMessage());
			return;
		} catch (Exception e) {
			showAlert("Error", e.getMessage());
			return;
		}
	}
}
