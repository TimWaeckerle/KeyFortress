package keyfortress.ui;

import java.util.UUID;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import keyfortress.domain.events.KeystoreEntryObservable;
import keyfortress.domain.keystore.KeystoreEntry;
import keyfortress.domain.repositories.FileSystemKeystoreRepository;
import keyfortress.domain.services.KeystoreService;

public class EditEntryForm extends Application {

	private UUID keystoreID;
	private KeystoreEntry keystoreEntry;
	private KeystoreEntryObservable observable;

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Edit Keystore Entry " + keystoreEntry.getName());

		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(20));
		gridPane.setHgap(10);
		gridPane.setVgap(10);

		Label nameLabel = new Label("Name:");
		TextField nameTextField = new TextField();
		nameTextField.setText(keystoreEntry.getName());
		Label passwordLabel = new Label("Password:");
		PasswordField passwordField = new PasswordField();

		gridPane.add(nameLabel, 0, 0);
		gridPane.add(nameTextField, 1, 0);
		gridPane.add(passwordLabel, 0, 1);
		gridPane.add(passwordField, 1, 1);

		Button saveButton = new Button("Save");
		saveButton.setOnAction(event -> {
			editEntry(nameTextField.getText(), passwordField.getText());
			primaryStage.close();
		});
		gridPane.add(saveButton, 1, 2);

		Scene scene = new Scene(gridPane, 300, 150);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void editEntry(String name, String password) {
		KeystoreService keystoreService = new KeystoreService(new FileSystemKeystoreRepository());
		try {
			keystoreService.updateKeystoreEntry(keystoreID, keystoreEntry, name, password);
			observable.notifyObservers();
		} catch (Exception e) {
			showAlert("Error", e.getMessage());
			e.printStackTrace();
		}
	}

	private void showAlert(String title, String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	public void setKeystoreEntry(KeystoreEntry keystoreEntry) {
		this.keystoreEntry = keystoreEntry;
	}

	public void setKeystoreID(UUID keystoreID) {
		this.keystoreID = keystoreID;
	}

	public void setObservable(KeystoreEntryObservable observable) {
		this.observable = observable;
	}
}
