package game;
import java.util.Random;

import javafx.scene.image.ImageView;
public class Meteorite extends Sprite implements GameWorld{
	private final Random random = new Random();
	private static final double METEORITE_WIDTH = 50;
	private static final double METEORITE_HEIGHT = 50;
	private boolean stopAnimation;
	
	public Meteorite(String image) {
		super(image);
		this.getImageView().setX(random.nextInt(SCENE_WIDTH - (int)METEORITE_WIDTH)); 
		this.getImageView().setY(0);
		this.getImageView().setFitWidth(METEORITE_WIDTH);
		this.getImageView().setFitHeight(METEORITE_HEIGHT);
	}

	public ImageView getMeteorite() {
		return this.getImageView();
	}
	
	public double getMeteoriteWidth() {
		return METEORITE_WIDTH;
	}
	
	public double getMeteoriteHeight(){
		return METEORITE_HEIGHT;
	}
	
	public boolean getAnimationStop() {
		return stopAnimation;
	}
	
	public void setAnimationStop(boolean stop) {
		this.stopAnimation = stop;
	}
	
}
