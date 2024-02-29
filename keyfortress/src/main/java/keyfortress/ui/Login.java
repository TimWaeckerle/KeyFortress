package keyfortress.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Login extends Application {

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Anmelden");

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		Label benutzernameLabel = new Label("Benutzername:");
		TextField benutzernameField = new TextField();
		Label passwortLabel = new Label("Passwort:");
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
		Button cancelButton = new Button("Register");

		loginButton.setOnAction(e -> {
			System.out.println("Login-Button wurde geklickt");
		});

		cancelButton.setOnAction(e -> {
			System.out.println("Cancel-Button wurde geklickt");
		});

		grid.add(loginButton, 0, 2);
		grid.add(cancelButton, 1, 2);

		Scene scene = new Scene(grid);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
