package ui;

import java.util.ArrayList;
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
import keystore.Keystore;
import observer.KeystoreObservable;
import observer.KeystoreObserver;
import password.strength.LengthPasswordEvaluationStrategy;
import password.strength.NumericPasswordEvaluationStrategy;
import password.strength.PasswordEvaluationStrategy;
import password.strength.PasswordStrengthEvaluator;
import password.strength.SpecialCharacterEvaluationStrategy;
import password.strength.UpperCaseEvaluationStrategy;
import persistence.FileSystemKeystoreRepository;
import persistence.FileSystemUserRepository;
import services.KeystoreService;
import services.UserService;
import user.User;

public class KeystoreOverviewForm extends KeyFortressUI implements KeystoreObserver {

	private UserService userService;
	private KeystoreService keystoreService;
	private KeystoreObservable keystoreObservable;
	private VBox vbox;

	@Override
	public void start(Stage primaryStage) {
		keystoreObservable = new KeystoreObservable();
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
		try {
			boolean verified = keystoreService.verifyPasswordForKeystore(keystore, askForPassword());
			if (verified) {
				KeystoreEntryForm keystoreForm = new KeystoreEntryForm(keystoreObservable, keystore);
				keystoreForm.start(new Stage());
			} else {
				showAlert("Error", "Wrong Password");
			}
		} catch (Exception e) {
			showAlert("Error", "Password can't be empty.");
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
		AddKeystoreForm addKeystoreForm = new AddKeystoreForm(keystoreObservable);
		addKeystoreForm.start(new Stage());
	}

	public void loadKeystores() {
		User user = userService.getLoggedInUser();
		List<UUID> keystoreIDs = keystoreService.getAllKeystoresForUser(user);
		vbox.getChildren().clear();

		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(5);

		int row = 0;

		for (UUID keystoreID : keystoreIDs) {
			Keystore keystore = keystoreService.getKeystoreByID(keystoreID);
			if (keystore != null) {
				Button button = new Button(keystore.getName());
				int passwordStrength = getPasswordEvaluator().evaluateKeystorePasswordStrength(keystore);

				Label strengthLabel = new Label("Strength: " + passwordStrength);
				strengthLabel.setStyle(getLabelColor(passwordStrength));

				gridPane.addRow(row++, button, strengthLabel);
				button.setOnAction(event -> handleKeystoreButtonClick(keystore));
			}
		}

		vbox.getChildren().add(gridPane);
	}

	private String getLabelColor(int passwordStrength) {
		String colorStyle = "-fx-text-fill: ";
		if (passwordStrength < 5) {
			colorStyle += "red;";
		} else if (passwordStrength < 8) {
			colorStyle += "gold;";
		} else {
			colorStyle += "green;";
		}
		return colorStyle;
	}

	private PasswordStrengthEvaluator getPasswordEvaluator() {
		List<PasswordEvaluationStrategy> strategies = new ArrayList<>();
		strategies.add(new LengthPasswordEvaluationStrategy());
		strategies.add(new NumericPasswordEvaluationStrategy());
		strategies.add(new SpecialCharacterEvaluationStrategy());
		strategies.add(new UpperCaseEvaluationStrategy());
		return new PasswordStrengthEvaluator(strategies);
	}

	@Override
	public void onChange() {
		loadKeystores();
	}
}
