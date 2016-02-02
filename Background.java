
/**
 * This class handles the scrolling of the background
 * 
 * @author Jabari
 *
 */
public class Background {

	private double bgX, bgY, speedX;

	/**
	 * Constructor. Places the background at a certain position and the speed at
	 * which it scrolls
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordiante
	 */
	public Background(int x, int y) {
		bgX = x;
		speedX = .5;
	}

	public void update() {
		bgX += speedX;
		if (bgX >= 480) {
			bgX = -660; // -660
		}
	}

	// ACCESSOR AND MUTATOR METHODS
	
	public double getBgX() {
		return bgX;
	}

	public double getBgY() {
		return bgY;
	}

	public double getSpeedX() {
		return speedX;
	}

	public void setBgX(int bgX) {
		this.bgX = bgX;
	}

	public void setBgY(int bgY) {
		this.bgY = bgY;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

}
