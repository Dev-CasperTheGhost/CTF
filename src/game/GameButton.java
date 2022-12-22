package game;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
// nut bam
public class GameButton extends Button {
	private Button button;
	//nut PLAY trong menu
	public GameButton(String text) {
		if(text=="PLAY")
		{	
				button = new Button("PLAY");
		}
	}
	public Button getButton() {
		return button;
	}

}
