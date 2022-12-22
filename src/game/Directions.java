package game;

import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

public enum Directions {

	UP(new Text("UP")),

	RIGHT(new Text("RIGHT")),

	DOWN(new Text("DOWN")),

	LEFT(new Text("LEFT"));
	
	final Text direction;

	Directions(Text direction) {
		this.direction = direction;

	}
	
	public Text toText() {
		return this.direction;
	}

	public KeyCode getKeyCode() {
		switch (this.toString()) {
		case "UP":
			return KeyCode.UP;
		case "RIGHT":
			return KeyCode.RIGHT;
		case "DOWN":
			return KeyCode.DOWN;
		case "LEFT":
			return KeyCode.LEFT;
		default:
			return null;
		}

	}
}
