
import java.util.Random;

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
		Random ran = new Random();
		setCenterX(centerX);
		setCenterY(centerY);

		// gives the ship a random number 0 - 40
		/**
		 * @kyle Au
		 * Special selection cases for multiplying mode and division mode
		 */
		if(StartingClass.getDivisionMode()){//
			setNumber(ran.nextInt(10-2)+2);
		
		}else if (StartingClass.getMulMode()){
			setNumber(ran.nextInt(100-2)+2);
		}else{
		setNumber(ran.nextInt(20)+2);
		}
	}
}
