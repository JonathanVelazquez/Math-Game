
import java.awt.Rectangle;

/**
 * This class handles the movement of the spaceships and their lasers, the hit
 * boxes, and whether or not it should be destroyed
 */
public class Enemy {
	private double centerX, centerY;

	// these are the hit boxes
	public Rectangle r = new Rectangle(0, 0, 0, 0);

	// how many shots from a laser it takes to destroy the ship
	public int health = 1;
	private int number;

	public final double MOVEMENT_SPEED = 0.5;
	private boolean isMovingY = true;

	private boolean isDead = false;

	/**
	 * Updates the position of the enemies
	 */
	public void update() {

		follow();
		createHitBox(this);
	}

	private void createHitBox(Enemy spaceship) {

		r.setBounds((int) centerX - 36, (int) centerY, 24, 60);

	}

	/**
	 * updates the position for downward spaceship laser
	 * 
	 */
	public void laserUpdate3() {

		// makes laser & ship stay coupled (maybe a better way to do this?)
		if (StartingClass.downEnemies.get(StartingClass.currentIndex).isMovingY()) {
			centerY = StartingClass.downEnemies.get(StartingClass.currentIndex).getCenterY();
			centerX = StartingClass.downEnemies.get(StartingClass.currentIndex).getCenterX();
		}
		if (StartingClass.MIDDLE_Y > StartingClass.downLasers.get(StartingClass.currentIndex).getCenterY()) {
			centerY += 3;
		} else {
			centerX = StartingClass.downEnemies.get(StartingClass.currentIndex).getCenterX() + 1;
			centerY = StartingClass.downEnemies.get(StartingClass.currentIndex).getCenterY();
		}
	}

	/**
	 * updates the position of each space ship
	 */
	public void follow() {

		// moves y position
		if (centerY < StartingClass.MIDDLE_Y - 100) {
			centerY += MOVEMENT_SPEED;
		} else {
			isMovingY = false;
		}
	}

	// ACCESSOR AND MUTATOR METHODS

	public double getCenterX() {
		return centerX;
	}

	public double getCenterY() {
		return centerY;
	}

	public void setCenterY(double centerY) {
		this.centerY = centerY;
	}
	
	public void setCenterX(double d) {
		this.centerX = d;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public boolean isMovingY() {
		return isMovingY;
	}

	public void setMovingY(boolean isMovingY) {
		this.isMovingY = isMovingY;
	}

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
}