package jabarigame;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Projectile {

	private int x, y, speedY;
	private boolean visible;

	private Rectangle r;

	public Projectile(int startX, int startY) {
		x = startX;
		y = startY;
		speedY = 7;
		visible = true;

		r = new Rectangle(0, 0, 0, 0);
	}

	public void update() {
		y -= speedY;
		r.setBounds(x, y, 10, 5);
		if (y > 800) {
			visible = false;
			r = null;
		}
		if (y < 800) {
			checkCollision();
		}
	}

	private void checkCollision() {

		for (int i = 0; i < StartingClass.rightEnemies.size(); i++) {
			if ((r.intersects(StartingClass.rightEnemies.get(i).r))
					|| (r.intersects(StartingClass.rightEnemies.get(i).r2))
					|| (r.intersects(StartingClass.rightEnemies.get(i).r3))
					|| (r.intersects(StartingClass.rightEnemies.get(i).r4))
					|| (r.intersects(StartingClass.rightEnemies.get(i).r5))
					|| (r.intersects(StartingClass.rightEnemies.get(i).r6))) {
				visible = false;

				if (StartingClass.rightEnemies.get(i).health > 0) {
					StartingClass.rightEnemies.get(i).health -= 1;
				}
				if (StartingClass.rightEnemies.get(i).health == 0) {
					StartingClass.rightEnemies.remove(i);
					StartingClass.rightLasers.remove(i);
					
					StartingClass.score += 5;
				}
			}

		}
		
		// left ship collision detection
		for (int i = 0; i < StartingClass.leftEnemies.size(); i++) {
			if ((r.intersects(StartingClass.leftEnemies.get(i).r))
					|| (r.intersects(StartingClass.leftEnemies.get(i).r2))
					|| (r.intersects(StartingClass.leftEnemies.get(i).r3))
					|| (r.intersects(StartingClass.leftEnemies.get(i).r4))
					|| (r.intersects(StartingClass.leftEnemies.get(i).r5))
					|| (r.intersects(StartingClass.leftEnemies.get(i).r6))) {
				visible = false;

				if (StartingClass.leftEnemies.get(i).health > 0) {
					StartingClass.leftEnemies.get(i).health -= 1;
				}
				if (StartingClass.leftEnemies.get(i).health == 0) {
					StartingClass.leftEnemies.remove(i);
					StartingClass.leftLasers.remove(i);

					StartingClass.score += 5;
				}
			}

		}

		for (int i = 0; i < StartingClass.downEnemies.size(); i++) {
			if (r.intersects(StartingClass.downEnemies.get(i).r)) {
				visible = false;

				if (StartingClass.downEnemies.get(i).health > 0) {
					StartingClass.downEnemies.get(i).health -= 1;
				}
				if (StartingClass.downEnemies.get(i).health == 0) {
					StartingClass.downEnemies.remove(i);
					StartingClass.downLasers.remove(i);
					
					StartingClass.score += 5;
				}
			}
		}
	}

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