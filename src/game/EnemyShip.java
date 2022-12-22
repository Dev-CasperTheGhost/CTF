package game;

import java.util.Random;

import javafx.scene.image.ImageView;

public class EnemyShip extends Sprite implements GameWorld {
	
	private final Random random = new Random();
	private static final double ENEMY_WIDTH = 50;
	private static final double ENEMY_HEIGHT = 50;
	private boolean stopAnimation;
	
	public EnemyShip(String image) {
		super(image);
		this.getImageView().setX(random.nextInt(SCENE_WIDTH - (int)ENEMY_WIDTH)); 
		this.getImageView().setY(0);
		this.getImageView().setFitWidth(ENEMY_WIDTH);
		this.getImageView().setFitHeight(ENEMY_HEIGHT);
	}
	
	public ImageView getEnemyShip() {
		return this.getImageView();
	}
	
	public double getEnemyWidth() {
		return ENEMY_WIDTH;
	}
	
	public double getEnemyHeight(){
		return ENEMY_HEIGHT;
	}
	
	public boolean getAnimationStop() {
		return stopAnimation;
	}
	
	public void setAnimationStop(boolean stop) {
		this.stopAnimation = stop;
	}
	

}
