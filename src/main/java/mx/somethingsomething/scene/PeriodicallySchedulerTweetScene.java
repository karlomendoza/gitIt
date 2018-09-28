package mx.somethingsomething.scene;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.io.FileUtils;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mx.somethingsomething.pojo.PeriodicallyScheduledTweet;
import mx.somethingsomething.services.PeriodicallyScheduledTweets;

public class PeriodicallySchedulerTweetScene {

	private DirectoryChooser imagesDirectoryChooser = new DirectoryChooser();
	private File imagesDirectory;

	private FileChooser fileChooser = new FileChooser();
	private File imageFile;

	private FileChooser fileChooserForTweets = new FileChooser();
	private File tweetsFile;

	private Map<Integer, ScheduledFuture> periodicallyScheduledTweets = new HashMap<Integer, ScheduledFuture>();
	private int maximunThreads = 10;
	private AtomicInteger idCounter = new AtomicInteger(0);

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

		// A checkbox with a string caption
		CheckBox useTweetList = new CheckBox("Use a list of tweets from a file");
		grid.add(useTweetList, 1, rowsCounter);
		rowsCounter++;

		final Button tweetButton = new Button("Select File with tweets");
		HBox hbBtn3 = new HBox(10);
		hbBtn3.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn3.getChildren().add(tweetButton);

		TextField pathToTweet = new TextField();

		// TODO remove this line only for tests
		tweetsFile = new File("C:\\Users\\Karlo Mendoza\\dev\\imagenes\\tweetIt\\1.jpg");
		tweetButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				tweetsFile = fileChooserForTweets.showOpenDialog(primaryStage);
				if (tweetsFile != null) {
					pathToTweet.setText(tweetsFile.getName());
				} else {
					pathToTweet.setText("");
				}
			}
		});

		Label tweetLabel = new Label("Write tweet");
		grid.add(tweetLabel, 1, rowsCounter);
		TextArea tweet = new TextArea("Write your tweet here");
		grid.add(tweet, 1, rowsCounter);
		rowsCounter++;

		// Remove/add buttons to load a file or a folder
		int rowCounterHoldTweet = rowsCounter - 1;
		useTweetList.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				if (useTweetList.isSelected()) {
					grid.getChildren().remove(tweetLabel);
					grid.getChildren().remove(tweet);

					grid.add(tweetButton, 0, rowCounterHoldTweet);
					grid.add(pathToTweet, 1, rowCounterHoldTweet);
				} else {
					grid.getChildren().remove(pathToTweet);
					grid.getChildren().remove(tweetButton);

					grid.add(tweetLabel, 0, rowCounterHoldTweet);
					grid.add(tweet, 1, rowCounterHoldTweet);
				}
			}
		});

		// A checkbox with a string caption
		CheckBox useSameImage = new CheckBox("Use the same image");
		grid.add(useSameImage, 1, rowsCounter);
		rowsCounter++;

		final Button imageButton = new Button("Select File to Load");
		HBox hbBtn2 = new HBox(10);
		hbBtn2.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn2.getChildren().add(imageButton);

		TextField metaDataPathToImage = new TextField();

		// TODO remove this line only for tests
		imageFile = new File("C:\\Users\\Karlo Mendoza\\dev\\imagenes\\tweetIt\\1.jpg");
		imageButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {

				imageFile = fileChooser.showOpenDialog(primaryStage);
				if (imageFile != null) {
					metaDataPathToImage.setText(imageFile.getName());
				} else {
					metaDataPathToImage.setText("");
				}
			}
		});

		final Button imagesDirectoryButton = new Button("Select Files Folder");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(imagesDirectoryButton);
		grid.add(imagesDirectoryButton, 0, rowsCounter);

		TextField metaDataPath = new TextField();
		grid.add(metaDataPath, 1, rowsCounter);
		rowsCounter++;

		// TODO remove this line only for tests
		imagesDirectory = new File("C:\\Users\\Karlo Mendoza\\dev\\imagenes\\tweetIt");
		imagesDirectoryButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				imagesDirectory = imagesDirectoryChooser.showDialog(primaryStage);
				if (imagesDirectory != null) {
					metaDataPath.setText(imagesDirectory.getName());
				} else {
					metaDataPath.setText("");
				}
			}
		});

		// Remove/add buttons to load a file or a folder
		int rowCounterHold = rowsCounter - 1;
		useSameImage.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				if (useSameImage.isSelected()) {
					grid.getChildren().remove(imagesDirectoryButton);
					grid.getChildren().remove(metaDataPath);

					grid.add(imageButton, 0, rowCounterHold);
					grid.add(metaDataPathToImage, 1, rowCounterHold);
				} else {
					grid.getChildren().remove(imageButton);
					grid.getChildren().remove(metaDataPathToImage);

					grid.add(imagesDirectoryButton, 0, rowCounterHold);
					grid.add(metaDataPath, 1, rowCounterHold);
				}
			}
		});

		Label timeUnitLabel = new Label("Select time unit");
		grid.add(timeUnitLabel, 0, rowsCounter);

		ObservableList<TimeUnit> options = FXCollections.observableArrayList(TimeUnit.SECONDS, TimeUnit.MINUTES,
				TimeUnit.HOURS, TimeUnit.DAYS);
		final ComboBox<TimeUnit> timeComboBox = new ComboBox<TimeUnit>(options);
		grid.add(timeComboBox, 1, rowsCounter);
		rowsCounter++;

		Label timeLenghtLabel = new Label("Select how frecuent");
		grid.add(timeLenghtLabel, 0, rowsCounter);

		TextField timeLenght = new TextField("");
		grid.add(timeLenght, 1, rowsCounter);
		rowsCounter++;

		// force the field to be numeric only
		timeLenght.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					timeLenght.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});

		ObservableList<PeriodicallyScheduledTweet> data = FXCollections.observableArrayList();
		// TODO esto no deberia estar aqui, igual solo al inicio en alguna
		// parte?
		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(maximunThreads);

		final Button processButton = new Button("Process");
		HBox processHbBtn = new HBox(10);
		processHbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		processHbBtn.getChildren().add(processButton);
		grid.add(processHbBtn, 0, rowsCounter);
		rowsCounter++;
		processButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				try {
					PeriodicallyScheduledTweets periodicallySendTweet;
					List<String> tweets = new ArrayList<String>();
					if (useTweetList.isSelected()) {
						tweets = FileUtils.readLines(tweetsFile, Charset.defaultCharset());
					}

					if (useSameImage.isSelected()) {
						if (useTweetList.isSelected()) {
							periodicallySendTweet = new PeriodicallyScheduledTweets(tweets, imageFile);
						} else {
							periodicallySendTweet = new PeriodicallyScheduledTweets(tweet.getText(), imageFile);
						}
					} else {
						if (useTweetList.isSelected()) {
							periodicallySendTweet = new PeriodicallyScheduledTweets(tweets, imagesDirectory);
						} else {
							periodicallySendTweet = new PeriodicallyScheduledTweets(tweet.getText(), imagesDirectory);
						}

					}
					periodicallySendTweet.setDaemon(true);

					ScheduledFuture<?> resultFuture = executorService.scheduleAtFixedRate(periodicallySendTweet, 0,
							Integer.valueOf(timeLenght.getText()), timeComboBox.getValue());

					periodicallyScheduledTweets.put(idCounter.get(), resultFuture);
					if (useSameImage.isSelected()) {
						data.add(new PeriodicallyScheduledTweet(idCounter.getAndIncrement(), tweet.getText(),
								timeComboBox.getValue(), Integer.valueOf(timeLenght.getText()), imageFile));
					} else {
						data.add(new PeriodicallyScheduledTweet(idCounter.getAndIncrement(), tweet.getText(),
								timeComboBox.getValue(), Integer.valueOf(timeLenght.getText()), imagesDirectory));
					}

					displayMessage(AlertType.INFORMATION, "Your tweet will be published on the selected schedule");
				} catch (Exception e1) {
					displayMessage(AlertType.INFORMATION, "We got some errors, please check the data");
					e1.printStackTrace();
				}
			}
		});

		TableView table = new TableView();
		table.setEditable(true);

		TableColumn idCol = new TableColumn("ID");
		idCol.setMinWidth(100);
		idCol.setCellValueFactory(new PropertyValueFactory<PeriodicallyScheduledTweet, Integer>("id"));

		TableColumn tweetCol = new TableColumn("Tweet");
		tweetCol.setMinWidth(100);
		tweetCol.setCellValueFactory(new PropertyValueFactory<PeriodicallyScheduledTweet, String>("tweet"));

		TableColumn timeUnitCol = new TableColumn("Time Unit");
		timeUnitCol.setMinWidth(200);
		timeUnitCol.setCellValueFactory(new PropertyValueFactory<PeriodicallyScheduledTweet, TimeUnit>("timeUnit"));

		TableColumn gapDurationCol = new TableColumn("Interval");
		gapDurationCol.setMinWidth(100);
		gapDurationCol
				.setCellValueFactory(new PropertyValueFactory<PeriodicallyScheduledTweet, Integer>("gapDuration"));

		TableColumn fileCol = new TableColumn("File/Directory Name");
		fileCol.setMinWidth(100);
		fileCol.setCellValueFactory(new PropertyValueFactory<PeriodicallyScheduledTweet, File>("file"));

		table.getColumns().addAll(idCol, tweetCol, timeUnitCol, gapDurationCol, fileCol);
		table.setItems(data);

		VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(table);
		grid.add(vbox, 1, rowsCounter);
		rowsCounter++;

		Task<Void> messageBoard = new Task<Void>() {
			@Override
			public Void call() throws InterruptedException {
				while (true) {
					Thread.sleep(3000);
					for (Iterator<PeriodicallyScheduledTweet> iterator = data.iterator(); iterator.hasNext();) {
						PeriodicallyScheduledTweet row = iterator.next();

						if (periodicallyScheduledTweets.get(row.getId()).isDone()) {
							iterator.remove();
						}
					}
				}
			}
		};

		Thread thread = new Thread(messageBoard);
		thread.setDaemon(true);
		thread.start();

		root.getChildren().add(grid);
		return root;

	}

	private static void displayMessage(AlertType severity, String message) {
		Alert alert = new Alert(severity);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
