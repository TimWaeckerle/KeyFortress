package keyfortress.ui;

import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import keyfortress.domain.keystore.Keystore;
import keyfortress.domain.repositories.FileSystemUserRepository;
import keyfortress.domain.services.UserService;

public class KeyStoreOverviewForm extends Application {

	private UserService userService;

	@Override
	public void start(Stage primaryStage) {
		userService = new UserService(new FileSystemUserRepository());
		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(10));

		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(10));
		gridPane.setHgap(10);
		gridPane.setVgap(10);

		updateKeystoreOverview(gridPane);

		borderPane.setCenter(gridPane);

		Button addKeystoreButton = new Button("Add Keystore");
		addKeystoreButton.setOnAction(event -> {
			handleAddKeystoreButtonClick();
			primaryStage.close();
		});
		StackPane.setAlignment(addKeystoreButton, Pos.BOTTOM_RIGHT);
		StackPane stackPane = new StackPane(addKeystoreButton);
		borderPane.setBottom(stackPane);

		Scene scene = new Scene(borderPane, 400, 300);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void handleKeystoreButtonClick(Keystore keystore) {
		Stage keystoreEntryStage = new Stage();
		keystoreEntryStage.setTitle(keystore.getName());
		KeystoreEntryForm keystoreForm = new KeystoreEntryForm();
		keystoreForm.setKeystore(keystore);
		keystoreForm.start(keystoreEntryStage);
		keystoreEntryStage.show();
	}

	private void handleAddKeystoreButtonClick() {
		Stage addKeystoreStage = new Stage();
		addKeystoreStage.setTitle("Add Keystore");
		AddKeystoreForm addKeystoreForm = new AddKeystoreForm();
		addKeystoreForm.start(addKeystoreStage);
		addKeystoreStage.show();
	}

	public void updateKeystoreOverview(GridPane gridPane) {
		List<Keystore> keystores = userService.getLoggedInUser().getKeystores();
		gridPane.getChildren().clear();
		int row = 0;
		for (Keystore keystore : keystores) {
			Button button = new Button(keystore.getName());
			button.setOnAction(event -> handleKeystoreButtonClick(keystore));
			gridPane.add(button, 0, row++);
		}
	}
}
