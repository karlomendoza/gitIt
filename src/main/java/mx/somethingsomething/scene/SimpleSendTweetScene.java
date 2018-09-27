package mx.somethingsomething.scene;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mx.somethingsomething.services.SendTweet;

public class SimpleSendTweetScene {

	private FileChooser fileChooser = new FileChooser();
	private File file;

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
		grid.add(scenetitle, 0, 0, 2, 1);

		final Button imagesDirectoryButton = new Button("Select File for tweet or empty");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(imagesDirectoryButton);
		grid.add(hbBtn, 0, rowsCounter);

		TextField metaDataPath = new TextField();
		grid.add(metaDataPath, 1, rowsCounter);
		rowsCounter++;

		// TODO remove this line only for tests
		file = new File("C:\\Users\\Karlo Mendoza\\dev\\tweetit\\processed\\4.jpg");
		imagesDirectoryButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				file = fileChooser.showOpenDialog(primaryStage);
				if (file != null) {
					metaDataPath.setText(file.getName());
				} else {
					metaDataPath.setText("");
				}
			}
		});

		Label tweetLabel = new Label("Write tweet");
		grid.add(tweetLabel, 0, rowsCounter);

		TextArea tweet = new TextArea("Write your tweet here");
		grid.add(tweet, 1, rowsCounter);
		rowsCounter++;

		final Button processButton = new Button("Tweet it");
		HBox processHbBtn = new HBox(10);
		processHbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		processHbBtn.getChildren().add(processButton);
		grid.add(processHbBtn, 0, rowsCounter);
		rowsCounter++;
		processButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				try {
					SendTweet.postToTwitter(tweet.getText(), file);
					displayMessage(AlertType.INFORMATION, "Run succesfully");
				} catch (Exception e1) {
					displayMessage(AlertType.INFORMATION, "Run with errors");
					e1.printStackTrace();
				}
			}
		});

		root.getChildren().add(grid);
		return root;

	}

	private static void configureFileChooser(final FileChooser fileChooser) {
		fileChooser.setTitle("Open Files");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.xlsx", "*.xls"));
	}

	private static void displayMessage(AlertType severity, String message) {
		Alert alert = new Alert(severity);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
