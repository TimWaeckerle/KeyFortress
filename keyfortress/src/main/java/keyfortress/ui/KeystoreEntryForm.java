package keyfortress.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
			Button passwordButton = new Button("Passwort");
			passwordButton.setOnAction(e -> {
				copyPasswordToClipboard(keystoreEntry);
			});

			vbox.getChildren().addAll(nameLabel, passwordButton);
		}
	}

	public void setKeystore(Keystore keystore) {
		this.keystore = keystore;
	}
}
