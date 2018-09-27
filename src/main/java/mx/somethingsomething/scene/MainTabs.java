package mx.somethingsomething.scene;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import mx.somethingsomething.application.TwitterApplication;

/**
 *
 */
public class MainTabs extends Application {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	public int a = 0;
	public TwitterApplication tApp = null;

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Authorization");
		Group root = new Group();
		Scene scene = new Scene(root, 900, 800, Color.WHITE);
		TabPane tabPane = new TabPane();
		BorderPane mainPane = new BorderPane();

		// Create Tabs
		Tab tabA = new Tab();
		tabA.setText("Authorization");
		AuthorizationScene authorizationScene = new AuthorizationScene();
		StackPane authorizationScenePane = authorizationScene.load(primaryStage);
		tabA.setContent(authorizationScenePane);
		tabPane.getTabs().add(tabA);

		Tab tabB = new Tab();
		tabB.setText("MultipleTweetScheduler");
		// Add something in Tab
		PeriodicallySchedulerTweetScene periodicallySchedulerTweetScene = new PeriodicallySchedulerTweetScene();
		StackPane multipleTweetSchedulerScenePane = periodicallySchedulerTweetScene.load(primaryStage);
		tabB.setContent(multipleTweetSchedulerScenePane);
		tabPane.getTabs().add(tabB);

		Tab tabC = new Tab();
		tabC.setText("Tweet Scheduler");
		// Add something in Tab
		SchedulerTweetScene schedulerTweetScene = new SchedulerTweetScene();
		StackPane schedulerTweetScenePane = schedulerTweetScene.load(primaryStage);
		tabC.setContent(schedulerTweetScenePane);
		tabPane.getTabs().add(tabC);

		Tab tabD = new Tab();
		tabD.setText("Send Tweet");
		// Add something in Tab
		SimpleSendTweetScene simpleSendTweetScene = new SimpleSendTweetScene();
		StackPane simpleSendTweetScenePane = simpleSendTweetScene.load(primaryStage);
		tabD.setContent(simpleSendTweetScenePane);
		tabPane.getTabs().add(tabD);

		mainPane.setCenter(tabPane);
		mainPane.prefHeightProperty().bind(scene.heightProperty());
		mainPane.prefWidthProperty().bind(scene.widthProperty());

		root.getChildren().add(mainPane);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}