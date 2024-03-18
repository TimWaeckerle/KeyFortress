package keyfortress.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import keyfortress.domain.exceptions.PasswordValidationException;
import keyfortress.domain.keystore.Keystore;
import keyfortress.domain.keystore.KeystorePassword;
import keyfortress.domain.repositories.FileSystemUserRepository;
import keyfortress.domain.services.UserService;
import keyfortress.domain.user.User;

public class AddKeystoreForm extends Application {

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Add Keystore");

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);

		Label nameLabel = new Label("Name:");
		TextField nameTextField = new TextField();
		Label passwordLabel = new Label("Password:");
		PasswordField passwordTextField = new PasswordField();

		grid.add(nameLabel, 0, 0);
		grid.add(nameTextField, 1, 0);
		grid.add(passwordLabel, 0, 1);
		grid.add(passwordTextField, 1, 1);

		Button addButton = new Button("Add");
		addButton.setOnAction(event -> {
			handleAddButtonClick(nameTextField.getText(), passwordTextField.getText());
			primaryStage.close();
		});

		grid.add(addButton, 1, 2);

		Scene scene = new Scene(grid);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void handleAddButtonClick(String name, String password) {
		UserService userService = new UserService(new FileSystemUserRepository());
		try {
			KeystorePassword keyPassword = new KeystorePassword(password);
			Keystore keystore = new Keystore(name, keyPassword);
			User user = userService.getLoggedInUser();
			user.addKeystores(keystore);
			userService.saveUser(user);
			openKeystore(user, keystore);

		} catch (PasswordValidationException e) {
			showAlert("Error", e.getMessage());
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	private void openKeystore(User user, Keystore keystore) {
		Stage keystoreStage = new Stage();
		keystoreStage.setTitle(user.getName());
		KeystoreEntryForm keyStoreForm = new KeystoreEntryForm();
		keyStoreForm.setKeystore(keystore);
		keyStoreForm.start(keystoreStage);
		keystoreStage.show();
	}

	private void showAlert(String title, String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
