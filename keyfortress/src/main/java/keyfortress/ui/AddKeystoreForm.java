package keyfortress.ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import keyfortress.application.services.KeystoreService;
import keyfortress.application.services.UserService;
import keyfortress.domain.events.KeystoreOverviewObservable;
import keyfortress.domain.exceptions.PasswordValidationException;
import keyfortress.domain.keystore.Keystore;
import keyfortress.domain.user.User;
import keyfortress.plugins.persistence.FileSystemKeystoreRepository;
import keyfortress.plugins.persistence.FileSystemUserRepository;

public class AddKeystoreForm extends KeyFortressUI {

	private Stage primaryStage;
	private KeystoreOverviewObservable keystoreObservable;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
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
		});

		grid.add(addButton, 1, 2);

		grid.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				addButton.fire();
			}
		});

		Scene scene = new Scene(grid);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void handleAddButtonClick(String name, String password) {
		UserService userService = new UserService(new FileSystemUserRepository());
		KeystoreService keystoreService = new KeystoreService(new FileSystemKeystoreRepository());
		try {
			User user = userService.getLoggedInUser();
			Keystore keystore = keystoreService.createKeystoreAndBindToUser(name, password, user);
			userService.addKeystoreToUser(keystore, user);
			openKeystore(user, keystore);
			primaryStage.close();
		} catch (PasswordValidationException e) {
			showAlert("Error", e.getMessage());
		} catch (Exception e) {
			showAlert("Error", e.getMessage());
		}
	}

	private void openKeystore(User user, Keystore keystore) {
		Stage keystoreStage = new Stage();
		keystoreStage.setTitle(user.getName());
		KeystoreEntryForm keyStoreForm = new KeystoreEntryForm();
		keyStoreForm.setKeystore(keystore);
		keyStoreForm.setKeystoreOverviewObservable(keystoreObservable);
		keyStoreForm.start(keystoreStage);
		keystoreObservable.notifyObservers();
		keystoreStage.show();
	}

	public void setKeystoreOverviewObservable(KeystoreOverviewObservable keystoreObservable) {
		this.keystoreObservable = keystoreObservable;
	}
}
