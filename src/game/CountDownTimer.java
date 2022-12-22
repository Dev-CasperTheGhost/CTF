package game;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;
public class CountDownTimer {

	private Timeline timeline;
	private Label countdownLbl = new Label();
	private Integer START_TIME;
	private IntegerProperty countdownSeconds;
	private boolean countDownOver;

	public CountDownTimer(int startTime, Ship myShip) {
		countDownOver = false;
		START_TIME = startTime;
		countdownSeconds = new SimpleIntegerProperty(START_TIME);

		countdownLbl.textProperty().bind(countdownSeconds.asString());
		countdownLbl.setTextFill(Color.RED);

		countdownSeconds.set(START_TIME);
		timeline = new Timeline();
		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(START_TIME), e -> {
			timeline.setOnFinished(event -> {
				if (myShip.getHitPoints().get() > 0)
					goToBossBattle(myShip);
			});
		}, new KeyValue(countdownSeconds, 0)));
	}

	public boolean getTimerDone() {
		return countDownOver;
	}

	public int getSeconds() {
		return countdownSeconds.get();
	}

	public void startCountDown() {
		timeline.playFromStart();
	}

	public void stopCountDown() {
		timeline.stop();
	}

	public void goToBossBattle(Ship myShip) {
		if (Main.DEBUG) System.out.println("Going to boss battle");
		countDownOver = true;
	}

	public Label getLabel() {
		return countdownLbl;
	}

}
