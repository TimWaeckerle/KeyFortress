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

public class RegistrationForm extends Application {

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Register");

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		Label usernameLabel = new Label("Benutzername:");
		TextField usernameField = new TextField();
		Label passwordLabel = new Label("Passwort:");
		PasswordField passwordField = new PasswordField();

		ColumnConstraints col1 = new ColumnConstraints();
		col1.setPercentWidth(40);
		ColumnConstraints col2 = new ColumnConstraints();
		col2.setPercentWidth(30);
		grid.getColumnConstraints().addAll(col1, col2);
		grid.add(usernameLabel, 0, 0);
		grid.add(usernameField, 1, 0);
		grid.add(passwordLabel, 0, 1);
		grid.add(passwordField, 1, 1);

		Button loginButton = new Button("Register");

		loginButton.setOnAction(e -> {
			showAlert("Notifcation", "Account created");
		});

		grid.add(loginButton, 0, 2);

		Scene scene = new Scene(grid);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	
	
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);	
        alert.showAndWait();
    }
}
