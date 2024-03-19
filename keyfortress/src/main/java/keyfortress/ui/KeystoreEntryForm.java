package keyfortress.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import keyfortress.domain.keystore.Keystore;
import keyfortress.domain.keystore.KeystoreEntry;
import keyfortress.domain.repositories.FileSystemUserRepository;
import keyfortress.domain.services.UserService;

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

		Button leaveKeystoreButton = new Button("Leave Keystore");
		leaveKeystoreButton.setOnAction(event -> handleLeaveKeystoreButtonClick(primaryStage));

		VBox buttonsBox = new VBox(10);
		buttonsBox.getChildren().addAll(addEntryButton, leaveKeystoreButton);
		buttonsBox.setAlignment(Pos.BOTTOM_RIGHT);

		vbox.getChildren().add(buttonsBox);

		Scene scene = new Scene(vbox, 300, 200);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Keystore Einträge");
		primaryStage.show();
	}

	private void copyPasswordToClipboard(KeystoreEntry keystoreEntry) {
		keystoreEntry.getPassword();
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
	}

	private void handleLeaveKeystoreButtonClick(Stage primaryStage) {
		UserService userService = new UserService(new FileSystemUserRepository());
		Stage keystoreOverview = new Stage();
		keystoreOverview.setTitle(userService.getLoggedInUser().getName());
		KeystoreOverviewForm keystoreOverviewForm = new KeystoreOverviewForm();
		keystoreOverviewForm.start(keystoreOverview);
		keystoreOverview.show();
	}

	public void setKeystore(Keystore keystore) {
		this.keystore = keystore;
	}
}
