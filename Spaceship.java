
import java.util.ArrayList;

/**
 * Creates a spaceship object and places it at a position on screen
 *
 */
public class Spaceship extends Enemy {

	/**
	 * Constructor.
	 * 
	 * @param centerX
	 *            the x coordinate to place the ship
	 * @param centerY
	 *            the y coordinate to place the ship
	 */
	public Spaceship(int centerX, int centerY) {
		setCenterX(centerX);
		setCenterY(centerY);
	}
}
