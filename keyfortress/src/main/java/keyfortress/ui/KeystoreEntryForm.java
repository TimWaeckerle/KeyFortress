package keyfortress.ui;

import java.util.UUID;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import keyfortress.domain.events.KeystoreEntryObservable;
import keyfortress.domain.events.KeystoreEntryObserver;
import keyfortress.domain.events.KeystoreOverviewObservable;
import keyfortress.domain.keystore.Keystore;
import keyfortress.domain.keystore.KeystoreEntry;
import keyfortress.domain.repositories.FileSystemKeystoreRepository;
import keyfortress.domain.services.KeystoreService;

public class KeystoreEntryForm extends Application implements KeystoreEntryObserver {

	private Keystore keystore;
	private VBox vbox;
	private KeystoreEntryObservable entryObservable;
	private KeystoreOverviewObservable keystoreObservable;

	@Override
	public void start(Stage primaryStage) {
		entryObservable = new KeystoreEntryObservable();
		entryObservable.addObserver(this);

		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(10));

		vbox = new VBox(10);
		vbox.setAlignment(Pos.TOP_LEFT);

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(vbox);
		scrollPane.setFitToWidth(true);
		scrollPane.setFitToHeight(true);

		Button addEntryButton = new Button("Add Entry");
		addEntryButton.setOnAction(event -> handleAddEntryButtonClick());

		Button deleteKeystoreButton = new Button("Delete Whole Keystore");
		deleteKeystoreButton.setOnAction(event -> handleDeleteKeystoreButtonClick(primaryStage));

		Button leaveKeystoreButton = new Button("Leave Keystore");
		leaveKeystoreButton.setOnAction(event -> handleLeaveKeystoreButtonClick(primaryStage));

		HBox buttonsBox = new HBox(10);
		buttonsBox.getChildren().addAll(addEntryButton, deleteKeystoreButton, leaveKeystoreButton);
		buttonsBox.setAlignment(Pos.CENTER);

		borderPane.setCenter(scrollPane);
		borderPane.setBottom(buttonsBox);

		Scene scene = new Scene(borderPane, 400, 300);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Keystore " + keystore.getName());
		primaryStage.show();

		if (keystore != null && keystore.getKeyEntries() != null) {
			loadKeystoreEntries();
		}
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
		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(10));

		int row = 0;
		for (KeystoreEntry keystoreEntry : keystore.getKeyEntries()) {
			Label nameLabel = new Label(keystoreEntry.getName());
			Button passwordButton = new Button("Copy Password");
			passwordButton.setOnAction(e -> {
				copyPasswordToClipboard(keystoreEntry);
			});

			Button deleteButton = new Button("Delete");
			deleteButton.setOnAction(e -> {
				handleDeleteEntryButtonClick(keystoreEntry);
			});

			gridPane.add(nameLabel, 0, row);
			gridPane.add(passwordButton, 1, row);
			gridPane.add(deleteButton, 2, row);
			row++;
		}

		vbox.getChildren().add(gridPane);
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
		addEntryForm.setObservable(entryObservable);
		addEntryForm.start(addKeystoreEntryStage);
		addKeystoreEntryStage.show();
	}

	private void handleDeleteKeystoreButtonClick(Stage stage) {
		KeystoreService keystoreService = new KeystoreService(new FileSystemKeystoreRepository());
		keystoreService.deleteKeystore(keystore);
		keystoreObservable.notifyObservers();
		showAlert("Information", "Keystore: " + keystore.getName() + " got deleted");
		stage.close();
	}

	private void handleLeaveKeystoreButtonClick(Stage primaryStage) {
		primaryStage.close();
	}

	private void handleDeleteEntryButtonClick(KeystoreEntry keystoreEntry) {
		KeystoreService keystoreService = new KeystoreService(new FileSystemKeystoreRepository());
		keystoreService.removeKeystoreEntry(keystore, keystoreEntry);
		entryObservable.notifyObservers();
	}

	public void setKeystore(Keystore keystore) {
		this.keystore = keystore;
	}

	public void setKeystoreOverviewObservable(KeystoreOverviewObservable keystoreObservable) {
		this.keystoreObservable = keystoreObservable;
	}

	@Override
	public void onKeystoreEntryChange() {
		loadKeystoreEntries();
	}

	private void showAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
