
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * This class handles the movement of the laser gun and when it is ready to fire
 *
 */
public class LaserGun {

	// movement speed of laser gun
	final int MOVESPEED = 5;

	// initial placemenet of laser gun
	private int centerX = 300;
	private int centerY = 377;

	private boolean movingLeft = false;
	private boolean movingRight = false;
	private boolean readyToFire = true;

	private int speedX = 0;
	private int speedY = 0;

	public static Rectangle rect = new Rectangle(0, 0, 0, 0);
	public static Rectangle rect2 = new Rectangle(0, 0, 0, 0);
	public static Rectangle rect3 = new Rectangle(0, 0, 0, 0);
	public static Rectangle rect4 = new Rectangle(0, 0, 0, 0);
	public static Rectangle yellowRed = new Rectangle(0, 0, 0, 0);

	private Projectile p;
	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

	/**
	 * Updates the position of the laser gun
	 */
	public void update() {

		// Moves laser left or right
		centerX += speedX;

		// Sets Y Position
		centerY = 730;

		// Prevents laser gun from moving off screen
		if (centerX + speedX <= 60) {
			centerX = 61;
		} else if (centerX + speedX >= 500) {
			centerX = 507;
		}

		rect.setRect(centerX - 34, centerY - 63, 68, 63);
		rect2.setRect(rect.getX(), rect.getY() + 63, 68, 63);
		rect3.setRect(rect.getX() - 26, rect.getY() + 32, 26, 20);
		rect4.setRect(rect.getX() + 68, rect.getY() + 32, 26, 20);
		yellowRed.setRect(centerX - 110, centerY - 110, 180, 180);
	}

	/**
	 * Sets movement speed to be positive
	 */
	public void moveRight() {
		speedX = MOVESPEED;
	}

	/**
	 * Sets movement speed to be negative
	 */
	public void moveLeft() {
		speedX = -MOVESPEED;
	}

	/**
	 * Stops the laser gun from moving right
	 */
	public void stopRight() {
		setMovingRight(false);
		stop();
	}

	/**
	 * Stops the laser gun from moving left
	 */
	public void stopLeft() {
		setMovingLeft(false);
		stop();
	}

	/**
	 * Stops the laser gun
	 */
	private void stop() {
		if (isMovingRight() == false && isMovingLeft() == false) {
			speedX = 0;
		}

	}

	/**
	 * Shoots a laser out of the laser gun as long as it is ready to fire
	 */
	public void shoot() {
		if (readyToFire) {
			p = new Projectile(centerX - 46, centerY - 90);
			projectiles.add(p);
		}
	}

	// ACCESSOR AND MUTATOR METHODS

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public int getSpeedX() {
		return speedX;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

	public boolean isMovingRight() {
		return movingRight;
	}

	public void setMovingRight(boolean movingRight) {
		this.movingRight = movingRight;
	}

	public boolean isMovingLeft() {
		return movingLeft;
	}

	public void setMovingLeft(boolean movingLeft) {
		this.movingLeft = movingLeft;
	}

	public ArrayList getProjectiles() {
		return projectiles;
	}

	public boolean isReadyToFire() {
		return readyToFire;
	}

	public void setReadyToFire(boolean readyToFire) {
		this.readyToFire = readyToFire;
	}

}