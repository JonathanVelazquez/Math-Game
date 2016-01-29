package jabarigame;

public class Background {

	private double bgX, bgY, speedX;

	public Background(int x, int y) {
		bgX = x;
		speedX = .5;
	}

	public void update() {
		bgX += speedX;
		if (bgX >= 480) {
			bgX = -660; //-480
		}
	}

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
