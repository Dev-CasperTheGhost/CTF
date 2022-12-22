package game;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class Sprite {
	private ImageView imageView;
	public Sprite(String image) {
		this.imageView = setImage(image);
	}
	public ImageView getImageView() {
		return imageView;
	}
	public ImageView setImage(String text) {
        Image im = new Image(this.getClass().getResourceAsStream(text));
		ImageView iv = new ImageView(im);
		return iv;
	}
}