package keyfortress.ui;

import java.util.UUID;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import keyfortress.domain.events.KeystoreEntryObservable;
import keyfortress.domain.events.KeystoreEntryObserver;
import keyfortress.domain.keystore.Keystore;
import keyfortress.domain.keystore.KeystoreEntry;
import keyfortress.domain.repositories.FileSystemKeystoreRepository;
import keyfortress.domain.services.KeystoreService;

public class KeystoreEntryForm extends Application implements KeystoreEntryObserver {

	private Keystore keystore;
	private VBox vbox;
	private KeystoreEntryObservable observable;

	@Override
	public void start(Stage primaryStage) {
		observable = new KeystoreEntryObservable();
		observable.addObserver(this);
		vbox = new VBox(10);
		vbox.setPadding(new Insets(10));

		if (keystore != null && keystore.getKeyEntries() != null) {
			loadKeystoreEntries();
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
		if (keystoreEntry != null && keystoreEntry.getPassword() != null) {
			try {
				String password = keystoreEntry.getPassword().getDecryptedPassword();
				Clipboard clipboard = Clipboard.getSystemClipboard();
				ClipboardContent content = new ClipboardContent();

				content.putString(password);
				clipboard.setContent(content);

			} catch (Exception e) {
				showAlert("Error", e.getMessage());
				e.printStackTrace();
			}
		}
	}

	private void loadKeystoreEntries() {
		vbox.getChildren().clear();
		reloadKeystore();
		for (KeystoreEntry keystoreEntry : keystore.getKeyEntries()) {
			HBox entryBox = new HBox(10);
			entryBox.setAlignment(Pos.CENTER_LEFT);

			Label nameLabel = new Label("Name: " + keystoreEntry.getName());
			Button passwordButton = new Button("Password");
			passwordButton.setOnAction(e -> {
				copyPasswordToClipboard(keystoreEntry);
			});

			entryBox.getChildren().addAll(nameLabel, passwordButton);

			vbox.getChildren().add(entryBox);
		}
	}

	private void reloadKeystore() {
		KeystoreService keystoreService = new KeystoreService(new FileSystemKeystoreRepository());
		UUID id = keystore.getKeystoreID();
		keystore = keystoreService.getKeystoreByID(id);
	}

	private void handleAddEntryButtonClick() {
		Stage addKeystoreEntryStage = new Stage();
		addKeystoreEntryStage.setTitle("Add Entry");
		AddKeystoreEntryForm addEntryForm = new AddKeystoreEntryForm();
		addEntryForm.setKeystoreID(keystore.getKeystoreID());
		addEntryForm.setObservable(observable);
		addEntryForm.start(addKeystoreEntryStage);
		addKeystoreEntryStage.show();
	}

	private void handleDeleteKeystoreButtonClick() {
		// TODO
		KeystoreService keystoreService = new KeystoreService(new FileSystemKeystoreRepository());
	}

	private void handleLeaveKeystoreButtonClick(Stage primaryStage) {
		primaryStage.close();
	}

	public void setKeystore(Keystore keystore) {
		this.keystore = keystore;
	}

	@Override
	public void onKeystoreEntryAdded() {
		loadKeystoreEntries();
	}

	private void showAlert(String title, String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
