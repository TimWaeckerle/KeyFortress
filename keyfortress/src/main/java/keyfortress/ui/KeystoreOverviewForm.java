package keyfortress.ui;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import keyfortress.application.services.KeystoreService;
import keyfortress.application.services.UserService;
import keyfortress.domain.events.KeystoreOverviewObservable;
import keyfortress.domain.events.KeystoreOverviewObserver;
import keyfortress.domain.keystore.Keystore;
import keyfortress.plugins.persistence.FileSystemKeystoreRepository;
import keyfortress.plugins.persistence.FileSystemUserRepository;

public class KeystoreOverviewForm extends KeyFortressUI implements KeystoreOverviewObserver {

	private UserService userService;
	private KeystoreService keystoreService;
	private KeystoreOverviewObservable keystoreObservable;
	private VBox vbox;

	@Override
	public void start(Stage primaryStage) {
		keystoreObservable = new KeystoreOverviewObservable();
		keystoreObservable.addObserver(this);
		userService = new UserService(new FileSystemUserRepository());
		keystoreService = new KeystoreService(new FileSystemKeystoreRepository());

		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(10));

		vbox = new VBox(10);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(10));

		loadKeystores();

		StackPane centerPane = new StackPane(new ScrollPane(vbox));
		StackPane.setAlignment(centerPane, Pos.CENTER);
		borderPane.setCenter(centerPane);

		Button addKeystoreButton = new Button("Add Keystore");
		addKeystoreButton.setOnAction(e -> handleAddKeystoreButtonClick());
		borderPane.setBottom(addKeystoreButton);
		BorderPane.setAlignment(addKeystoreButton, Pos.CENTER);

		Scene scene = new Scene(borderPane, 400, 300);
		primaryStage.setScene(scene);
		primaryStage.setTitle(userService.getLoggedInUser().getName());
		primaryStage.show();
	}

	private void handleKeystoreButtonClick(Keystore keystore) {
		boolean verified = keystoreService.verifyPasswordForKeystore(keystore, askForPassword());
		if (verified) {
			KeystoreEntryForm keystoreForm = new KeystoreEntryForm();
			keystoreForm.setKeystore(keystore);
			keystoreForm.setKeystoreOverviewObservable(keystoreObservable);
			keystoreForm.start(new Stage());
		} else {
			showAlert("Error", "Wrong Password");
		}
	}

	private String askForPassword() {
		AtomicReference<String> passwordRef = new AtomicReference<>();

		Stage passwordStage = new Stage();
		passwordStage.setTitle("Enter Password");

		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(20));
		gridPane.setHgap(10);
		gridPane.setVgap(10);

		PasswordField passwordField = new PasswordField();

		Button confirmButton = new Button("Confirm");
		confirmButton.setOnAction(event -> {
			passwordStage.close();
			passwordRef.set(passwordField.getText());
		});

		passwordField.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				confirmButton.fire();
			}
		});

		gridPane.addRow(0, new Label("Password:"), passwordField);
		gridPane.addRow(1, confirmButton);

		Scene passwordScene = new Scene(gridPane);
		passwordStage.setScene(passwordScene);
		passwordStage.showAndWait();

		return passwordRef.get();
	}

	private void handleAddKeystoreButtonClick() {
		AddKeystoreForm addKeystoreForm = new AddKeystoreForm();
		addKeystoreForm.setKeystoreOverviewObservable(keystoreObservable);
		addKeystoreForm.start(new Stage());
	}

	public void loadKeystores() {
		List<UUID> keystoreIDs = userService.getLoggedInUser().getKeystores();

		vbox.getChildren().clear();
		for (UUID keystoreID : keystoreIDs) {
			Keystore keystore = keystoreService.getKeystoreByID(keystoreID);
			if (keystore != null) {
				Button button = new Button(keystore.getName());
				button.setOnAction(event -> handleKeystoreButtonClick(keystore));
				vbox.getChildren().add(button);
			}
		}
	}

	@Override
	public void onKeystoreChange() {
		loadKeystores();
	}
}
