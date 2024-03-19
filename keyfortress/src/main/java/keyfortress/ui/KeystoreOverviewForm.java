package keyfortress.ui;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import keyfortress.domain.keystore.Keystore;
import keyfortress.domain.repositories.FileSystemKeystoreRepository;
import keyfortress.domain.repositories.FileSystemUserRepository;
import keyfortress.domain.services.KeystoreService;
import keyfortress.domain.services.UserService;

public class KeystoreOverviewForm extends Application {

	private UserService userService;
	private KeystoreService keystoreService;
	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) {
		userService = new UserService(new FileSystemUserRepository());
		keystoreService = new KeystoreService(new FileSystemKeystoreRepository());
		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(10));
		this.primaryStage = primaryStage;
		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(10));
		gridPane.setHgap(10);
		gridPane.setVgap(10);

		updateKeystoreOverview(gridPane);

		borderPane.setCenter(gridPane);

		Button addKeystoreButton = new Button("Add Keystore");
		addKeystoreButton.setOnAction(e -> handleAddKeystoreButtonClick());
		StackPane.setAlignment(addKeystoreButton, Pos.BOTTOM_RIGHT);
		StackPane stackPane = new StackPane(addKeystoreButton);
		borderPane.setBottom(stackPane);

		Scene scene = new Scene(borderPane, 400, 300);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void handleKeystoreButtonClick(Keystore keystore) {
		boolean true1 = keystoreService.verifyPasswordForKeystore(keystore, askForPassword());
		if (true1) {
			Stage keystoreEntryStage = new Stage();
			keystoreEntryStage.setTitle(keystore.getName());
			KeystoreEntryForm keystoreForm = new KeystoreEntryForm();
			keystoreForm.setKeystore(keystore);
			keystoreForm.start(keystoreEntryStage);
			keystoreEntryStage.show();
			primaryStage.close();
		} else {
			showAlert("Error", "Wrong Password");
		}
	}

	private void showAlert(String title, String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
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
		Stage addKeystoreStage = new Stage();
		addKeystoreStage.setTitle("Add Keystore");
		AddKeystoreForm addKeystoreForm = new AddKeystoreForm();
		addKeystoreForm.start(addKeystoreStage);
		addKeystoreStage.show();
		primaryStage.close();
	}

	public void updateKeystoreOverview(GridPane gridPane) {
		List<UUID> keystores = userService.getLoggedInUser().getKeystores();

		gridPane.getChildren().clear();
		int row = 0;
		for (UUID keystoreID : keystores) {
			Keystore keystore = keystoreService.getKeystoreByID(keystoreID);
			if (keystore != null) {
				Button button = new Button();
				button.setText(keystore.getName());
				button.setOnAction(event -> handleKeystoreButtonClick(keystore));
				gridPane.add(button, 0, row++);
			}
		}
	}
}
