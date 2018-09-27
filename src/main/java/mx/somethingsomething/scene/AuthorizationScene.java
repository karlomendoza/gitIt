package mx.somethingsomething.scene;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mx.somethingsomething.application.TwitterApplication;

public class AuthorizationScene {

	public StackPane load(Stage primaryStage) {

		int rowsCounter = 1;

		StackPane root = new StackPane();

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(50, 50, 50, 50));

		Text scenetitle = new Text("Please load all fields");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 1, 1);

		if (!TwitterApplication.isUserAuthenticated()) {

			Label authorizationLabel = new Label(
					"Enter this url and allow us permission");
			grid.add(authorizationLabel, 0, rowsCounter);

			rowsCounter++;
			TextField authorizationURLLabel = new TextField(
					TwitterApplication.getAuthorizationURL());

			authorizationURLLabel.setEditable(false);
			grid.add(authorizationURLLabel, 0, rowsCounter);
			rowsCounter++;

			Label pinNumberLabel = new Label("Enter pin number");
			grid.add(pinNumberLabel, 0, rowsCounter);

			rowsCounter++;
			TextField pinNumber = new TextField("");
			grid.add(pinNumber, 0, rowsCounter);
			rowsCounter++;

			final Button processButton = new Button("Authorize");
			HBox processHbBtn = new HBox(10);
			processHbBtn.setAlignment(Pos.BOTTOM_RIGHT);
			processHbBtn.getChildren().add(processButton);
			grid.add(processHbBtn, 0, rowsCounter);
			rowsCounter++;
			processButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(final ActionEvent e) {

					try {
						TwitterApplication.authorize(pinNumber.getText());

						displayMessage(AlertType.INFORMATION,
								"Run succesfully");
					} catch (Exception e1) {
						displayMessage(AlertType.INFORMATION,
								"Run with errors");
						e1.printStackTrace();
					}
				}
			});
		} else {
			Label pinNumberLabel = new Label(
					"You have an accout already linked");
			grid.add(pinNumberLabel, 0, rowsCounter);
		}

		root.getChildren().add(grid);
		return root;
	}

	private static void displayMessage(AlertType severity, String message) {
		Alert alert = new Alert(severity);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
