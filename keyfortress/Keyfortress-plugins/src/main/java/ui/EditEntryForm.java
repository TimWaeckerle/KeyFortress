package ui;

import java.util.UUID;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import keystore.KeystoreEntry;
import observer.KeystoreObservable;
import persistence.FileSystemKeystoreRepository;
import services.KeystoreService;

public class EditEntryForm extends KeyFortressUI {

	private UUID keystoreID;
	private KeystoreEntry keystoreEntry;
	private KeystoreObservable observable;

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
			keystoreService.updateKeystoreEntry(keystoreID, keystoreEntry, password, name);
			observable.notifyObservers();
		} catch (Exception e) {
			showAlert("Error", e.getMessage());
			e.printStackTrace();
		}
	}

	public void setKeystoreEntry(KeystoreEntry keystoreEntry) {
		this.keystoreEntry = keystoreEntry;
	}

	public void setKeystoreID(UUID keystoreID) {
		this.keystoreID = keystoreID;
	}

	public void setObservable(KeystoreObservable observable) {
		this.observable = observable;
	}
}
