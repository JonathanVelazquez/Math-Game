
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * Handles when a laser from the laser gun hits a spaceship
 *
 */
public class Projectile {

	private int x, y, speedY;
	private boolean visible;

	private Rectangle r;

	/**
	 * Constructor. Initializes the speed of the laser, the visibility of the
	 * laser, and creates a new rectangle object
	 * 
	 * @param startX
	 *            the initial x position of the laser
	 * @param startY
	 *            the initial y position of the laser
	 */
	public Projectile(int startX, int startY) {
		x = startX;
		y = startY;
		speedY = 7;
		visible = true;

		r = new Rectangle(0, 0, 0, 0);
	}

	/**
	 * Updates the position of each laser along with the laser's hit box
	 */
	public void update() {
		y -= speedY;
		r.setBounds(x, y, 10, 5);

		// removes the visibility of the laser when it reaches the end of the
		// screen
		if (y > 800) {
			visible = false;
			r = null;
		} else {
			checkCollision();
		}
	}

	/**
	 * Checks to see if the laser collides with a spaceship
	 */
	private void checkCollision() {

		for (int i = 0; i < StartingClass.downEnemies.size(); i++) {

			/*
			 * if a laser hits a ship, the visibility is removed, and the health
			 * of the ship that was hit decreases by 1. If the ship's health is
			 * 0 it is removed from the list and the player's score increases by
			 * 5 points
			 */
			if (r.intersects(StartingClass.downEnemies.get(i).r)) {
				visible = false;

				if (StartingClass.downEnemies.get(i).health > 0) {
					StartingClass.downEnemies.get(i).health -= 1;
				}
				if (StartingClass.downEnemies.get(i).health == 0) {
					StartingClass startingClass = new StartingClass();
					if (startingClass.getNumber1() + startingClass.getNumber2() == StartingClass.downEnemies.get(i).getNumber()) {
						StartingClass.readyForNewEquation = true;
						StartingClass.score += 1;
					} else {
						StartingClass.score -= 2;
						StartingClass.health -= 2;
					}
					StartingClass.downEnemies.remove(i);
					StartingClass.downLasers.remove(i);
				}
			}
		}
	}

	// ACCESSOR AND MUTATOR METHODS

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getSpeedY() {
		return speedY;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setSpeedY(int speedX) {
		this.speedY = speedX;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}