package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
public class GameView implements GameWorld {
	private static final int GAME_TIME = 50;

	private final Random random = new Random();
	private boolean isGameOver = false;
	private boolean skipBattle = false;

	private static final int SHIP_SPEED = 400;
	private static final int BULLET_SPEED = 2;
	private static final int MAX_ENEMIES = 9;

	private boolean spaceRepeat = false;
	private final Text scoreCounter;
	private int enemyNumber = 2;

	private List<TranslateTransition> animationList;
	private List<Timeline> timelineList;
	private List<EnemyShip> enemies;
	private Ship myShip;
	private Scene gameScene;
	private Group gameRoot;
	private CountDownTimer timer;
	private AnimationTimer shipAnimation;
	private ParallelTransition scrollingBackground;

	public GameView() {
		animationList = new ArrayList<TranslateTransition>();
		timelineList = new ArrayList<Timeline>();
		enemies = new ArrayList<EnemyShip>();
		scoreCounter = new Text();

		isGameOver = false;
	
		myShip = new Ship("images/MainShip.png");
		timer = new CountDownTimer(GAME_TIME, myShip);
		gameRoot = new Group();
	}

	public void animateGame() {
		timer.startCountDown();
		EnemyShip enemy = createEnemy();
		animateEnemy(enemy);
	}

	public Scene initGame() {
		gameScene = new Scene(gameRoot, SCENE_WIDTH, SCENE_HEIGHT);

		Group backgroundGroup = new Group();
		scrollBackground(backgroundGroup);
		gameRoot.getChildren().add(backgroundGroup);
		gameRoot.getChildren().add(timer.getLabel());
		gameRoot.getChildren().add(myShip.getImageView());

		createScoreCounter();

		LongProperty lastUpdateTime = new SimpleLongProperty();
		shipAnimation = new AnimationTimer() {
			@Override
			public void handle(long timestamp) {
				
				handleShipMovement(lastUpdateTime, timestamp);
			}
		};
		shipAnimation.start();
		handleKeyPressed();
		handleKeyReleased();
		return gameScene;
	}

	private void handleShipMovement(LongProperty lastUpdateTime, long timestamp) {
		double deltaX;
		double oldX;
		double newX;
		if (lastUpdateTime.get() > 0) {
			final double elapsedSeconds = (timestamp - lastUpdateTime.get()) / 1_000_000_000.0;
			// distance moved = rate * time
			deltaX = myShip.getShipVelocity().get() * elapsedSeconds;
			oldX = myShip.getImageView().getTranslateX();
			newX = Math.max(0, Math.min(SCENE_WIDTH - myShip.getImageView().getFitWidth(), oldX + deltaX));
			myShip.getImageView().setTranslateX(newX);
		} else {
			myShip.getImageView().setTranslateX(0);
		}
		lastUpdateTime.set(timestamp);
		if (isGameOver || timer.getTimerDone()) {
			stopAllAnimation();
		}
	}

	private void handleKeyPressed() {
		gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.RIGHT) {
					myShip.setShipVelocity(SHIP_SPEED);
				} else if (event.getCode() == KeyCode.LEFT) {
					myShip.setShipVelocity(-SHIP_SPEED);
				} else if (event.getCode() == KeyCode.SPACE) {
					if (!spaceRepeat && !isGameOver) {
						spaceRepeat = true;
						fireBullet(enemies);
					}
				} else if (event.getCode() == KeyCode.D) {
					getInfiniteLives();
				} else if (event.getCode() == KeyCode.F) {
					getInfiniteAmmo();
				} else if (event.getCode() == KeyCode.S) {
					skipBattle = true;
				} else if (event.getCode() == KeyCode.A) {
					myShip.setHitPoints(0);
					checkHitPoints();
				}
			}
		});
	}

	private void handleKeyReleased() {
		gameScene.setOnKeyReleased(e -> {
			if (e.getCode() == KeyCode.RIGHT) {
				if (myShip.getShipVelocity().get() > 0) {
					myShip.setShipVelocity(0);
				}
			} else if (e.getCode() == KeyCode.LEFT) {
				if (myShip.getShipVelocity().get() < 0) {
					myShip.setShipVelocity(0);
				}
			} else if (e.getCode() == KeyCode.SPACE) {
				spaceRepeat = false;
			}
		});
	}

	private void createScoreCounter() {
		scoreCounter.textProperty().bind(Bindings.concat("Score: ").concat(myShip.getScore()).concat("\nHit Points: ")
				.concat(myShip.getHitPoints()).concat("\nBullets: ").concat(myShip.getAmmo()).concat("\n"));
		scoreCounter.setTextAlignment(TextAlignment.CENTER);
		scoreCounter.setLayoutX(SCENE_WIDTH / 2 - 43);
		scoreCounter.setLayoutY(20);
		scoreCounter.setFill(Color.RED);
		gameRoot.getChildren().add(scoreCounter);
	}

	private void updateScoreCounter() {
		scoreCounter.textProperty().bind(Bindings.concat("Score: ").concat(myShip.getScore()).concat("\nHit Points: ")
				.concat(myShip.getHitPoints()).concat("\nBullets: ").concat(myShip.getAmmo()));
	}

	private void fireBullet(final List<EnemyShip> enemies) {
		Shape bullet = new Circle(2.3, Color.GREENYELLOW);
		if (myShip.getAmmo() <= 0) {
			// do nothing
			if (Main.DEBUG)
				System.out.println("Out of ammo!");
		} else {
			myShip.setAmmo(myShip.getAmmo() - 1);
			gameRoot.getChildren().add(bullet);
			TranslateTransition animation = new TranslateTransition(Duration.seconds(BULLET_SPEED), bullet);
			animationList.add(animation);
			setAnimationBounds(animation, myShip.getImageView().getTranslateX() + SHIP_WIDTH / 2,
					SCENE_HEIGHT - myShip.getImageView().getFitHeight(),
					myShip.getImageView().getTranslateX() + SHIP_WIDTH / 2, -40);
			animation.play();

			bullet.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
				@Override
				public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
					for (EnemyShip enemy : new ArrayList<EnemyShip>(enemies)) {
						if (bullet.getBoundsInParent().intersects(enemy.getEnemyShip().getBoundsInParent())) {
							handleBulletDestroyedEnemy(bullet, animation, enemy);
						}
					}
				}
			});
			setOnBulletAnimationFinished(bullet, animation);
			updateScoreCounter();
		}
	}

	private void setAnimationBounds(TranslateTransition animation, double fromX, double fromY, double toX, double toY) {
		animation.setFromX(fromX);
		animation.setFromY(fromY);
		animation.setToX(toX);
		animation.setToY(toY);
	}

	private void handleBulletDestroyedEnemy(Shape bullet, TranslateTransition animation, EnemyShip enemy) {
		if (Main.DEBUG)
			System.out.println("Hit enemy!");
		cleanUpEnemy(enemy);
		animation.stop();
		gameRoot.getChildren().remove(bullet);
		// Creates two enemies on hit every three hits (offset by 2 to start)
		// and if # enemies < MAX_ENEMIES
		if (enemyNumber % 3 == 0 && enemies.size() < MAX_ENEMIES) {
			for (int i = 0; i < 2; i++) {
				animateEnemy(createEnemy());
			}
		} else {
			animateEnemy(createEnemy());
		}
		myShip.incrementPlayerScore();
		addAmmoOnHit();
		enemyNumber++;
	}

	@SuppressWarnings("unused")
	@Deprecated
	private void moveEnemyShip(EnemyShip enemy) {
		TranslateTransition animation = new TranslateTransition(Duration.seconds(random.nextInt(2) + 1),
				enemy.getEnemyShip());
		animationList.add(animation);
		animation.setFromX(enemy.getEnemyShip().getTranslateX());
		animation.setFromY(enemy.getEnemyShip().getTranslateY());
		int rand = random.nextInt(SCENE_WIDTH - (int) enemy.getEnemyWidth());
		animation.setToX(rand);
		animation.setToY(enemy.getEnemyShip().getTranslateY() + random.nextInt(100) + 40);

		checkYBounds(enemy);

		animation.setOnFinished(e -> {
			if (enemy.getAnimationStop()) {
				animation.stop();
			}
			moveEnemyShip(enemy);
		});
		if (!enemy.getAnimationStop()) {
			animation.play();
		}
	}

	private void moveEnemyShip2(EnemyShip enemy) {
		Timeline timeline = new Timeline();
		timelineList.add(timeline);

		KeyFrame end = getEnemyMovementKeyFrame(enemy);

		timeline.getKeyFrames().add(end);
		timeline.setCycleCount(1);
		timeline.setOnFinished(e -> {
			checkYBounds(enemy);
			if (enemy.getAnimationStop()) {
				timeline.stop();
			} else {
				timeline.getKeyFrames().remove(0);
				timeline.getKeyFrames().add(getEnemyMovementKeyFrame(enemy));
				timeline.playFromStart();
			}
		});
		if (!enemy.getAnimationStop()) {
			timeline.playFromStart();
		}
	}

	private KeyFrame getEnemyMovementKeyFrame(EnemyShip enemy) {
		KeyFrame kf = new KeyFrame(Duration.millis(random.nextInt(2300) + 1000),
				new KeyValue(enemy.getEnemyShip().xProperty(),
						random.nextInt(SCENE_WIDTH - (int) enemy.getEnemyWidth())),
				new KeyValue(enemy.getEnemyShip().yProperty(),
						(int) enemy.getEnemyShip().getY() + random.nextInt(100) + 40));
		return kf;
	}

	private EnemyShip createEnemy() {
		EnemyShip enemy = new EnemyShip("images/ufo.png");
		enemies.add(enemy);
		gameRoot.getChildren().add(enemy.getEnemyShip());
		return enemy;
	}
	private void animateEnemy(EnemyShip enemy) {
		moveEnemyShip2(enemy);
		Timeline timeline = new Timeline();
		timelineList.add(timeline);
		KeyFrame key1 = new KeyFrame(Duration.millis(random.nextInt(2000) + 1000),
				e -> enemyFireAnimation(timeline, enemy));
		timeline.getKeyFrames().add(key1);
		timeline.setCycleCount(1);
		timeline.setOnFinished(e -> {
			if (enemy.getAnimationStop()) {
				timeline.stop();
			} else {
				timeline.play();
			}
		});
		timeline.play();
	}
	
	private void enemyFireAnimation(Timeline timeline, EnemyShip enemy) {
		if (Main.DEBUG)
			System.out.println("EnemyFireAnimation Method Called");
		if (enemy.getAnimationStop()) {
			timeline.stop();
		} else {
			enemyFire(enemy);
		}
	}
	private void enemyFire(EnemyShip enemy) {
		ImageView ship = enemy.getEnemyShip();
		Shape enemyBullet = new Circle(4, Color.ORANGERED);
		gameRoot.getChildren().add(enemyBullet);
		TranslateTransition animation = new TranslateTransition(Duration.seconds(random.nextInt(3) + 1), enemyBullet);
		animationList.add(animation);
		setAnimationBounds(animation, ship.getX() + enemy.getEnemyWidth() / 2, ship.getY() + enemy.getEnemyHeight(),
				ship.getX() + enemy.getEnemyWidth() / 2, SCENE_HEIGHT + 40);
		animation.play();

		enemyBullet.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {

			@Override
			public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
				if (enemyBullet.getBoundsInParent().intersects(myShip.getImageView().getBoundsInParent())) {
					if (Main.DEBUG)
						System.out.println("Enemy bullet hit me");
					gameRoot.getChildren().remove(enemyBullet);
					myShip.decrementHitPoints();
					checkHitPoints();
					animation.stop();
				}
			}
		});
		setOnBulletAnimationFinished(enemyBullet, animation);
	}
	private void setOnBulletAnimationFinished(Shape bullet, TranslateTransition animation) {
		animation.setOnFinished(e -> {
			gameRoot.getChildren().remove(bullet);
			animation.stop();
		});
	}
	private void checkYBounds(EnemyShip enemy) {
		if (enemy.getEnemyShip().getY() >= (SCENE_HEIGHT)) {
			if (Main.DEBUG)
				System.out.println("Removed enemy ship at bottom of screen");
			cleanUpEnemy(enemy);
			EnemyShip en = createEnemy();
			animateEnemy(en);
		}
	}

	private void checkHitPoints() {
		if (myShip.getHitPoints().get() <= 0) {
			gameRoot.getChildren().remove(myShip.getImageView());
			if (Main.DEBUG)
				System.out.println("You lost all of your HitPoints");
			isGameOver = true;
		}
	}

	private void cleanUpEnemy(EnemyShip enemy) {
		enemy.setAnimationStop(true);
		enemies.remove(enemy);
		gameRoot.getChildren().remove(enemy.getEnemyShip());
	}

	private void stopAllAnimation() {
		shipAnimation.stop();
		scrollingBackground.stop();
		for (TranslateTransition animation : animationList) {
			if (animation != null)
				animation.stop();
		}
		for (Timeline timeline : timelineList) {
			if (timeline != null)
				timeline.stop();
		}
	}

	private void scrollBackground(Group group) {
		ImageView iv = getBackgroundImageView("images/gameBackground.gif");
		ImageView iv2 = getBackgroundImageView("images/gameBackground2.gif");
		iv2.setY(SCENE_HEIGHT);

		group.getChildren().addAll(iv, iv2);

		TranslateTransition animation = getBackgroundTransition(iv);
		TranslateTransition animation2 = getBackgroundTransition(iv2);

		scrollingBackground = new ParallelTransition(animation, animation2);
		scrollingBackground.setCycleCount(Animation.INDEFINITE);
		scrollingBackground.play();
	}

	private TranslateTransition getBackgroundTransition(ImageView iv) {
		TranslateTransition animation = new TranslateTransition(Duration.seconds(5), iv);
		animation.setFromY(0);
		animation.setToY(-1 * SCENE_HEIGHT);
		animation.setInterpolator(Interpolator.LINEAR);
		return animation;
	}

	private ImageView getBackgroundImageView(String text) {
		Image im = new Image(GameView.class.getResourceAsStream(text));
		ImageView iv = new ImageView(im);
		return iv;
	}

	private void getInfiniteLives() {
		myShip.setHitPoints(10000);
		updateScoreCounter();
	}

	private void getInfiniteAmmo() {
		myShip.setAmmo(1000000);
		updateScoreCounter();
	}

	private void addAmmoOnHit() {
		myShip.setAmmo(myShip.getAmmo() + 2);
		updateScoreCounter();
	}

	public Ship getShip() {
		return myShip;
	}

	public CountDownTimer getTimer() {
		return timer;
	}

	public boolean isSkipBattle() {
		return skipBattle;
	}

	public boolean getGameOver() {
		return isGameOver;
	}

	public Scene getGameScene() {
		return gameScene;
	}

}
