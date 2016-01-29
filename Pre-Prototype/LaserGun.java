package jabarigame;

import java.awt.Rectangle;
import java.util.ArrayList;

public class LaserGun {

	final int MOVESPEED = 5;

	private int centerX = 100;
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

	public void update() {
		// Moves laser

		if (speedX < 0) {
			centerX += speedX;
		}

		if (centerX <= 500 && speedX > 0) {
			centerX += speedX;
		}

		// Sets Y Position
		centerY = 730;

		// Prevents going beyond X coordinate of 0
		if (centerX + speedX <= 60) {
			centerX = 61;
		}

		rect.setRect(centerX - 34, centerY - 63, 68, 63);
		rect2.setRect(rect.getX(), rect.getY() + 63, 68, 63);
		rect3.setRect(rect.getX() - 26, rect.getY() + 32, 26, 20);
		rect4.setRect(rect.getX() + 68, rect.getY() + 32, 26, 20);
		yellowRed.setRect(centerX - 110, centerY - 110, 180, 180);
	}

	public void moveRight() {
		speedX = MOVESPEED;
	}

	public void moveLeft() {
		speedX = -MOVESPEED;
	}

	public void stopRight() {
		setMovingRight(false);
		stop();
	}

	public void stopLeft() {
		setMovingLeft(false);
		stop();
	}

	private void stop() {
		if (isMovingRight() == false && isMovingLeft() == false) {
			speedX = 0;
		}

	}

	public void shoot() {
		if (readyToFire) {
			p = new Projectile(centerX - 46, centerY - 90);
			projectiles.add(p);
		}
	}

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