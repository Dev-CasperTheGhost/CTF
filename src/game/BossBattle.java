package game;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
public class BossBattle implements GameWorld {
	private Scene bossScene;
	private VBox vbox;
	private List<Text> inputList = new ArrayList<>();
	private int textNum = 0;
	private boolean gameOverLost = false;
	private boolean gameOverWon = false;
	public BossBattle() {
		gameOverLost = false;
		gameOverWon = false;
		List<Text> textList = new ArrayList<>();
		BorderPane root = setRootAndScene();
		fillDialogList(textList);
		createLaunchTimerAndButtons(textList, root);
		
	}
	private BorderPane setRootAndScene() {
		BorderPane bp = new BorderPane();
		bp.getStyleClass().add("bossBackground");
		bossScene = new Scene(bp, SCENE_WIDTH, SCENE_HEIGHT);
		bossScene.getStylesheets().add(BossBattle.class.getResource("GameStyle.css").toExternalForm());
		return bp;
	}
	private void fillDialogList(List<Text> textList) {
		textList.add(new Text("Kẻ thù đã bị tiêu diệt. Bạn là người hùng. Tiếp tục hành trình thôi nào!"));
		for (Text txt : textList) {
			txt.setFill(Color.WHITE);
			txt.setFont(Font.font("verdana", 15));

		}
	}
	private void createLaunchTimerAndButtons(List<Text> textList, BorderPane root) {
		createVBox();
		vbox.getChildren().addAll(textList.get(textNum));
		root.setBottom(vbox);
	}
	private void createVBox() {
		vbox = new VBox(10);
		vbox.setPadding(new Insets(50, 0, 70, 0));
		vbox.setAlignment(Pos.CENTER);
	}
	@SuppressWarnings("unused")
	private List<KeyCode> translateInputListToKeyCodes() {
		List<KeyCode> inputs = new ArrayList<>();
		for (Text text : inputList) {
			switch (text.getText()) {
			case "UP":
				inputs.add(KeyCode.UP);
				break;
			case "RIGHT":
				inputs.add(KeyCode.RIGHT);
				break;
			case "DOWN":
				inputs.add(KeyCode.DOWN);
				break;
			case "LEFT":
				inputs.add(KeyCode.LEFT);
				break;
			default:
			}
		}
		return inputs;

	}

	public boolean getGameOverLost() {
		return gameOverLost;
	}

	public boolean getGameOverWon() {
		return gameOverWon;
	}

	public Scene getScene() {
		return bossScene;
	}
}
