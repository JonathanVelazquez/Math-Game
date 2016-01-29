package jabarigame;

import java.awt.Rectangle;

public class Enemy {

	private int speedX, speedY;
	private double centerX, centerY;

	public Rectangle r = new Rectangle(0, 0, 0, 0);
	public Rectangle r2 = new Rectangle(0, 0, 0, 0);
	public Rectangle r3 = new Rectangle(0, 0, 0, 0);
	public Rectangle r4 = new Rectangle(0, 0, 0, 0);
	public Rectangle r5 = new Rectangle(0, 0, 0, 0);
	public Rectangle r6 = new Rectangle(0, 0, 0, 0);

	public int health = 2;
	
	private double movementSpeedX, movementSpeedY;
	private boolean isMovingX = true;
	private boolean isMovingY = true;

	private boolean isDead = false;

	// Behavioral Methods
	public void update() {

		follow();
		createHitBox(this);
	}

	private void createHitBox(Enemy spaceship) {

		// Right Ship hit box
		if (spaceship.getCenterX() < 200) {

			r.setBounds(
					(int) StartingClass.rightEnemies.get(
							StartingClass.currentIndex).getCenterX() + 28,
					(int) StartingClass.rightEnemies.get(
							StartingClass.currentIndex).getCenterY() - 20, 10,
					60);
			r2.setBounds(
					(int) StartingClass.rightEnemies.get(
							StartingClass.currentIndex).getCenterX() + 18,
					(int) StartingClass.rightEnemies.get(
							StartingClass.currentIndex).getCenterY() - 25, 10,
					60);
			r3.setBounds(
					(int) StartingClass.rightEnemies.get(
							StartingClass.currentIndex).getCenterX() + 8,
					(int) StartingClass.rightEnemies.get(
							StartingClass.currentIndex).getCenterY() - 30, 10,
					60);
			r4.setBounds(
					(int) StartingClass.rightEnemies.get(
							StartingClass.currentIndex).getCenterX() - 2,
					(int) StartingClass.rightEnemies.get(
							StartingClass.currentIndex).getCenterY() - 40, 10,
					60);
			r5.setBounds(
					(int) StartingClass.rightEnemies.get(
							StartingClass.currentIndex).getCenterX() - 12,
					(int) StartingClass.rightEnemies.get(
							StartingClass.currentIndex).getCenterY() - 50, 10,
					60);
			r6.setBounds(
					(int) StartingClass.rightEnemies.get(
							StartingClass.currentIndex).getCenterX() - 22,
					(int) StartingClass.rightEnemies.get(
							StartingClass.currentIndex).getCenterY() - 60, 10,
					60);
		}

		// left ship hit box
		else if (spaceship.getCenterX() > 300) {
			r.setBounds(
					(int) StartingClass.leftEnemies.get(
							StartingClass.currentIndex).getCenterX() - 40,
					(int) StartingClass.leftEnemies.get(
							StartingClass.currentIndex).getCenterY() - 20, 10,
					60);
			r2.setBounds(
					(int) StartingClass.leftEnemies.get(
							StartingClass.currentIndex).getCenterX() - 30,
					(int) StartingClass.leftEnemies.get(
							StartingClass.currentIndex).getCenterY() - 25, 10,
					60);
			r3.setBounds(
					(int) StartingClass.leftEnemies.get(
							StartingClass.currentIndex).getCenterX() - 20,
					(int) StartingClass.leftEnemies.get(
							StartingClass.currentIndex).getCenterY() - 30, 10,
					60);
			r4.setBounds(
					(int) StartingClass.leftEnemies.get(
							StartingClass.currentIndex).getCenterX() - 10,
					(int) StartingClass.leftEnemies.get(
							StartingClass.currentIndex).getCenterY() - 40, 10,
					60);
			r5.setBounds(
					(int) StartingClass.leftEnemies.get(
							StartingClass.currentIndex).getCenterX(),
					(int) StartingClass.leftEnemies.get(
							StartingClass.currentIndex).getCenterY() - 50, 10,
					60);
			r6.setBounds(
					(int) StartingClass.leftEnemies.get(
							StartingClass.currentIndex).getCenterX() + 10,
					(int) StartingClass.leftEnemies.get(
							StartingClass.currentIndex).getCenterY() - 60, 10,
					60);
		}

		// down ship hit box
		else {
			r.setBounds((int) centerX - 36, (int) centerY, 24, 60);
		}
	}

	// update for Right facing spaceship laser
	public void laserUpdate1() {

		// makes laser & ship stay coupled (maybe a better way to do this?)
		if (StartingClass.rightEnemies.get(StartingClass.currentIndex)
				.isMovingY()) {
			centerY = StartingClass.rightEnemies
					.get(StartingClass.currentIndex).getCenterY() - 25;
			centerX = StartingClass.rightEnemies
					.get(StartingClass.currentIndex).getCenterX() + 44;
		}

		if (StartingClass.MIDDLE_X > StartingClass.rightLasers.get(
				StartingClass.currentIndex).getCenterX()) {
			centerX += 3;
		} else {
			centerX = StartingClass.rightEnemies
					.get(StartingClass.currentIndex).getCenterX() + 44;
			centerY = StartingClass.rightEnemies
					.get(StartingClass.currentIndex).getCenterY() - 25;
		}

		if (StartingClass.MIDDLE_Y > StartingClass.rightLasers.get(
				StartingClass.currentIndex).getCenterY()) {
			centerY += 3;
		} else {
			centerX = StartingClass.rightEnemies
					.get(StartingClass.currentIndex).getCenterX() + 44;
			centerY = StartingClass.rightEnemies
					.get(StartingClass.currentIndex).getCenterY() - 25;
		}

		shiftRightShips();
	}

	// update for Left facing spaceship laser
	public void laserUpdate2() {
		// makes laser & ship stay coupled (maybe a better way to do this?)
		if (StartingClass.leftEnemies.get(StartingClass.currentIndex)
				.isMovingY()) {
			centerX = StartingClass.leftEnemies.get(StartingClass.currentIndex)
					.getCenterX();
			centerY = StartingClass.leftEnemies.get(StartingClass.currentIndex)
					.getCenterY() - 25;
		}

		if (StartingClass.MIDDLE_X < StartingClass.leftLasers.get(
				StartingClass.currentIndex).getCenterX()) {
			centerX -= 3;
		} else {
			centerX = StartingClass.leftEnemies.get(StartingClass.currentIndex)
					.getCenterX();
			centerY = StartingClass.leftEnemies.get(StartingClass.currentIndex)
					.getCenterY() - 25;
		}

		if (StartingClass.MIDDLE_Y > StartingClass.leftLasers.get(
				StartingClass.currentIndex).getCenterY()) {
			centerY += 3;
		} else {
			centerX = StartingClass.leftEnemies.get(StartingClass.currentIndex)
					.getCenterX();
			centerY = StartingClass.leftEnemies.get(StartingClass.currentIndex)
					.getCenterY() - 25;
		}
		
		shiftLeftShips();
	}

	// update for downward spaceship laser
	public void laserUpdate3() {

		// makes laser & ship stay coupled (maybe a better way to do this?)
		if (StartingClass.downEnemies.get(StartingClass.currentIndex)
				.isMovingY()) {
			centerY = StartingClass.downEnemies.get(StartingClass.currentIndex)
					.getCenterY();
			centerX = StartingClass.downEnemies.get(StartingClass.currentIndex)
					.getCenterX();
		}
		if (StartingClass.MIDDLE_Y > StartingClass.downLasers.get(
				StartingClass.currentIndex).getCenterY()) {
			centerY += 3;
		} else {
			centerX = StartingClass.downEnemies.get(StartingClass.currentIndex)
					.getCenterX() + 1;
			centerY = StartingClass.downEnemies.get(StartingClass.currentIndex)
					.getCenterY();
		}
		
		shiftDownShips();
	}

	// movement of ships
	public void follow() {
		if (StartingClass.MIDDLE_X - centerX < -100) {
			movementSpeedX = 1;
			centerX -= movementSpeedX;
		} else if (StartingClass.MIDDLE_X - centerX > 150) {
			movementSpeedX = 1;
			centerX += movementSpeedX;
		} else {
			movementSpeedX = 0;
			centerX += 0;
		}

		if (centerY < StartingClass.MIDDLE_Y - 100) {
			movementSpeedY = 1;
			centerY += movementSpeedY;
		} else {
			movementSpeedY = 0;
			centerY += movementSpeedY;
		}

		if (movementSpeedX == 0) {
			isMovingX = false;
		}

		if (movementSpeedY == 0) {
			isMovingY = false;
		}

	}

	// moves ships when they are in the same approximate position
	public void shiftRightShips() {
		for (int i = 0; i < StartingClass.rightEnemies.size(); i++) {
			for (int j = 0; j < StartingClass.rightEnemies.size(); j++) {
				if (i != j) {
					if ((!StartingClass.rightEnemies.get(j).isMovingX())
							&& (!StartingClass.rightEnemies.get(j).isMovingY())) {
						if ((!StartingClass.rightEnemies.get(i).isMovingX())
								&& (!StartingClass.rightEnemies.get(i)
										.isMovingY())) {
							if (StartingClass.rightEnemies.get(j).getCenterX() == StartingClass.rightEnemies
									.get(i).getCenterX()) {
								StartingClass.rightEnemies.get(j).setCenterX(
										StartingClass.rightEnemies.get(j)
												.getCenterX() + 1);
								/*System.out.println(i
										+ " "
										+ StartingClass.rightEnemies.get(i)
												.getCenterX());
								/System.out.println(j
										+ " "
										+ StartingClass.rightEnemies.get(j)
												.getCenterX());*/
								
								//System.out.println(i + " " + j + " equal");
							}
						}
					}
				}
			}
		}
	}
	
	public void shiftLeftShips() {
		for (int i = 0; i < StartingClass.leftEnemies.size(); i++) {
			for (int j = 0; j < StartingClass.leftEnemies.size(); j++) {
				if (i != j) {
					if ((!StartingClass.leftEnemies.get(j).isMovingX())
							&& (!StartingClass.leftEnemies.get(j).isMovingY())) {
						if ((!StartingClass.leftEnemies.get(i).isMovingX())
								&& (!StartingClass.leftEnemies.get(i)
										.isMovingY())) {
							if (StartingClass.leftEnemies.get(j).getCenterX() == StartingClass.leftEnemies
									.get(i).getCenterX()) {
								StartingClass.leftEnemies.get(j).setCenterX(
										StartingClass.leftEnemies.get(j)
												.getCenterX() - 1);
								/*System.out.println(i
										+ " "
										+ StartingClass.leftEnemies.get(i)
												.getCenterX());
								/System.out.println(j
										+ " "
										+ StartingClass.leftEnemies.get(j)
												.getCenterX());*/
								
								//System.out.println(i + " " + j + " equal");
							}
						}
					}
				}
			}
		}
	}
	
	public void shiftDownShips() {
		for (int i = 0; i < StartingClass.downEnemies.size(); i++) {
			for (int j = 0; j < StartingClass.downEnemies.size(); j++) {
				if (i != j) {
					if ((!StartingClass.downEnemies.get(j).isMovingX())
							&& (!StartingClass.downEnemies.get(j).isMovingY())) {
						if ((!StartingClass.downEnemies.get(i).isMovingX())
								&& (!StartingClass.downEnemies.get(i)
										.isMovingY())) {
							if (StartingClass.downEnemies.get(j).getCenterX() == StartingClass.downEnemies
									.get(i).getCenterX()) {
								StartingClass.downEnemies.get(j).setCenterX(
										StartingClass.downEnemies.get(j)
												.getCenterX() + 1);
								/*System.out.println(i
										+ " "
										+ StartingClass.downEnemies.get(i)
												.getCenterX());
								/System.out.println(j
										+ " "
										+ StartingClass.downEnemies.get(j)
												.getCenterX());*/
								
								//System.out.println(i + " " + j + " equal");
							}
						}
					}
				}
			}
		}
	}

	public void run() {

	}

	public void die() {

	}

	public void attack() {

	}

	public int getSpeedX() {
		return speedX;
	}

	public double getCenterX() {
		return centerX;
	}

	public double getCenterY() {
		return centerY;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public void setCenterX(double d) {
		this.centerX = d;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

	public boolean isMovingX() {
		return isMovingX;
	}

	public boolean isMovingY() {
		return isMovingY;
	}

	public void setMovingX(boolean isMovingX) {
		this.isMovingX = isMovingX;
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
}