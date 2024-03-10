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

public class LoginForm extends Application {

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Login");

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		Label benutzernameLabel = new Label("Username:");
		TextField benutzernameField = new TextField();
		Label passwortLabel = new Label("Password:");
		PasswordField passwortField = new PasswordField();

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(40);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(30);
        grid.getColumnConstraints().addAll(col1, col2);
		grid.add(benutzernameLabel, 0, 0);
		grid.add(benutzernameField, 1, 0);
		grid.add(passwortLabel, 0, 1);
		grid.add(passwortField, 1, 1);

		Button loginButton = new Button("Login");
		Button registerButton = new Button("Register");

		loginButton.setOnAction(e -> {
			System.out.println("Login-Button wurde geklickt");
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

	private void startRegisterUi() {
		Stage registrationStage = new Stage();
        registrationStage.setTitle("Registration Form");
        RegistrationForm registrationForm = new RegistrationForm();
        registrationForm.start(registrationStage);
        registrationStage.show();
	}
}
