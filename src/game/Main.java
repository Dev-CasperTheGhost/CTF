package game;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
public class Main extends Application implements GameWorld {
	// bien DEBUG
	public static boolean DEBUG = true;

	private BorderPane root;
	private Stage mainStage;
	private Scene scene;

	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		mainStage = primaryStage;
		root = new BorderPane();
		// nut bam play
		GameButton startBtn = createButtons();
		scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
		scene.getStylesheets().add(Main.class.getResource("GameStyle.css").toExternalForm());
		mainStage.setTitle("Team2");
		initGame(startBtn);
	}
	private GameButton createButtons() {
		VBox selections = new VBox(10);
		VBox titleBox = new VBox(10);
		styleItems(titleBox, selections);

		GameButton startBtn = new GameButton("PLAY");
		
		selections.getChildren().addAll(startBtn.getButton());

		root.setTop(titleBox);
		root.setCenter(selections);
		return startBtn;
	}
 void initGame(GameButton startBtn) {
				GameView game = new GameView();
		Scene gameScene = game.initGame();
		BossBattle boss = new BossBattle();
		startBtn.getButton().setOnAction(e -> initMainGame(game, gameScene));
		isGameOver(game, boss, startBtn);
		mainStage.setScene(scene);
		mainStage.show();
	}
	private void isGameWon(GameView game, GameButton startBtn, BossBattle boss) {
		Timeline timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000 / 60), e -> {
			if (boss.getGameOverWon()) {
				timeline.stop();
			
			} else if (boss.getGameOverLost()) {
				timeline.stop();
				createGameOverLost(startBtn);
			}
		}));
		timeline.play();
	}

	private void initMainGame(GameView game, Scene gameScene) {
		mainStage.setScene(gameScene);
		mainStage.show();
		game.animateGame();
	}
	private void isGameOver(GameView game, BossBattle boss, GameButton startBtn) {
		Timeline timeline = new Timeline();
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000 / 60), e -> {
			if (game.getTimer().getTimerDone() || game.isSkipBattle()) {
				if (DEBUG)
					System.out.println("Timer done");
				timeline.stop();
				mainStage.setScene(boss.getScene());
				mainStage.setTitle("Boss Battle!");
				isGameWon(game, startBtn, boss);
			} else if (game.getGameOver()) {
				timeline.stop();
				createGameOverLost(startBtn);
			}
		}));
		timeline.play();
	}
	private void createGameOverLost(GameButton startBtn) {
		BorderPane newRoot = new BorderPane();
		newRoot.getStyleClass().add("gameOverLost");
		newRoot.getStylesheets().add(BossBattle.class.getResource("GameStyle.css").toExternalForm());
//		newRoot.setStyle("-fx-background-color: black");
//		newRoot.setStyle("gameOverLost");
		mainStage.setScene(new Scene(newRoot, SCENE_WIDTH, SCENE_HEIGHT));
		mainStage.setTitle("Game Over!");

		Text text = new Text("Bạn đã thất bại và bạn có muốn thử lại hay không");
		text.setFont(Font.font("verdana", 15));
		text.setTextAlignment(TextAlignment.CENTER);
		text.setFill(Color.GHOSTWHITE);

		Button btn = new Button("Retry");
		btn.setOnMouseClicked(e -> initGame(startBtn));

		newRoot.setCenter(text);
		newRoot.setBottom(btn);
		BorderPane.setAlignment(btn, Pos.BASELINE_CENTER);
	}
	private void styleItems(VBox titleBox, VBox buttons) {
		buttons.setAlignment(Pos.CENTER);
		titleBox.setAlignment(Pos.CENTER);
		titleBox.setPadding(new Insets(60, 0, 0, 0));
		titleBox.getChildren().add(GAME_TITLE);
		GAME_TITLE.getStyleClass().add("gameTitle");
		root.getStyleClass().add("startScreen");
	}

}
