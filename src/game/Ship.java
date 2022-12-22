package game;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
public class Ship extends Sprite implements GameWorld {
	private final IntegerProperty playerHitPoints = new SimpleIntegerProperty();
	private final IntegerProperty playerScore = new SimpleIntegerProperty();
	private final SimpleDoubleProperty shipXVelocity = new SimpleDoubleProperty();
	private static final int AMMO_LIMIT = 10000;
	private static final int LIVES = 3;
	private int AMMO = AMMO_LIMIT;
	public Ship(String image) {
		super(image);
		this.getImageView().setX(0);
		this.getImageView().setY(SCENE_HEIGHT - SHIP_HEIGHT - 5);
		this.getImageView().setFitWidth(SHIP_WIDTH);
		this.getImageView().setFitHeight(SHIP_HEIGHT);
		this.playerHitPoints.set(LIVES);
	}
	public IntegerProperty getScore() {
		return playerScore;
	}
	
	public SimpleDoubleProperty getShipVelocity() {
		return shipXVelocity;
	}
	
	public void setShipVelocity(int i) {
		this.shipXVelocity.set(i);
	}
	
	public int getAmmo() { 
		return AMMO;
	}
	
	public void setAmmo(int i) {
		this.AMMO = i;
	}

	public void decrementHitPoints() {
		this.playerHitPoints.set(playerHitPoints.get() - 1);
	}
	
	public void setHitPoints(int i) {
		this.playerHitPoints.set(i);
	}
	
	public IntegerProperty getHitPoints() {
		return playerHitPoints;
	}
	
	public void incrementPlayerScore() {
		this.playerScore.set(playerScore.get() + 1);
	}

}
