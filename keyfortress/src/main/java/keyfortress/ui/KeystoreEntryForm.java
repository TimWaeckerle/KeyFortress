package keyfortress.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import keyfortress.domain.keystore.Keystore;
import keyfortress.domain.keystore.KeystoreEntry;

public class KeystoreEntryForm extends Application {

	private Keystore keystore;

	@Override
	public void start(Stage primaryStage) {
		VBox vbox = new VBox(10);
		vbox.setPadding(new Insets(10));

		if (keystore != null && keystore.getKeyEntries() != null) {
			loadKeystoreEntries(vbox);
		}

		Button addEntryButton = new Button("Add Entry");
		addEntryButton.setOnAction(event -> handleAddEntryButtonClick());

		Button deleteKeystoreButton = new Button("Delete Whole Keystore");
		deleteKeystoreButton.setOnAction(event -> handleDeleteKeystoreButtonClick());

		Button leaveKeystoreButton = new Button("Leave Keystore");
		leaveKeystoreButton.setOnAction(event -> handleLeaveKeystoreButtonClick(primaryStage));

		HBox buttonsBox = new HBox(10);
		buttonsBox.getChildren().addAll(addEntryButton, deleteKeystoreButton, leaveKeystoreButton);
		buttonsBox.setPadding(new Insets(10));
		buttonsBox.setAlignment(Pos.CENTER);

		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(vbox);
		borderPane.setBottom(buttonsBox);

		Scene scene = new Scene(borderPane, 300, 200);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Keystore Einträge");
		primaryStage.show();
	}

	private void copyPasswordToClipboard(KeystoreEntry keystoreEntry) {
		// Implementieren Sie hier die Logik zum Kopieren des Passworts in die
		// Zwischenablage
	}

	private void loadKeystoreEntries(VBox vbox) {
		for (KeystoreEntry keystoreEntry : keystore.getKeyEntries()) {
			Label nameLabel = new Label("Name: " + keystoreEntry.getName());
			Button passwordButton = new Button("Password");
			passwordButton.setOnAction(e -> {
				copyPasswordToClipboard(keystoreEntry);
			});

			vbox.getChildren().addAll(nameLabel, passwordButton);
		}
	}

	private void handleAddEntryButtonClick() {
		Stage addKeystoreEntryStage = new Stage();
		addKeystoreEntryStage.setTitle("Add Entry");
		AddKeystoreEntryForm addEntryForm = new AddKeystoreEntryForm();
		addEntryForm.setKeystoreID(keystore.getKeystoreID());
		addEntryForm.start(addKeystoreEntryStage);
		addKeystoreEntryStage.show();
	}

	private void handleDeleteKeystoreButtonClick() {
	}

	private void handleLeaveKeystoreButtonClick(Stage primaryStage) {
	}

	public void setKeystore(Keystore keystore) {
		this.keystore = keystore;
	}
}
