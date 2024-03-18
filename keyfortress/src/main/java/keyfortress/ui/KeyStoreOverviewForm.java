package keyfortress.ui;

import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import keyfortress.domain.keystore.Keystore;
import keyfortress.domain.repositories.FileSystemUserRepository;
import keyfortress.domain.services.UserService;

public class KeyStoreOverviewForm extends Application {

	private UserService userService;

	@Override
	public void start(Stage primaryStage) {
		userService = new UserService(new FileSystemUserRepository());
		List<Keystore> keystores = loadKeystores();

		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(10));
		gridPane.setHgap(10);
		gridPane.setVgap(10);

		int row = 0;
		for (Keystore keystore : keystores) {
			Button button = new Button(keystore.getName());
			button.setOnAction(event -> handleKeystoreButtonClick(keystore));
			gridPane.add(button, 0, row++);
		}

		Scene scene = new Scene(gridPane, 400, 300);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private List<Keystore> loadKeystores() {
		List<Keystore> keystores = userService.getAllKeystoresForUser("userId");
		return keystores;
	}

	private void handleKeystoreButtonClick(Keystore keystore) {
		// TODO launch own Keystore
		System.out.println("Button clicked for Keystore: " + keystore.getName());
	}
}
