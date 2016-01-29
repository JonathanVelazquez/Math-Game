package jabarigame;

import jabarigame.framework.Animation;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class StartingClass extends Applet implements Runnable, KeyListener {

	public static final int MIDDLE_X = 262; // approximate middle x value
	public static final int MIDDLE_Y = 415; // approximate middle y value

	enum GameState {
		Running, Dead, MainMenu
	}

	GameState state = GameState.Running;

	private static LaserGun laserGun;

	public static Spaceship spaceships, spaceships2, spaceships3, spaceship1,
			spaceship2, spaceship3, laser, laserDown, laserRight, laserLeft;

	public static int score = 0;
	private double health = 150; // 150

	private Font font = new Font(null, Font.BOLD, 30);

	private Image image, background, spaceshipDown, spaceshipRight,
			spaceshipLeft, laser1, laser2, laser3;

	private Image currentLaserGun, laserGunImage;

	private Image earth, destroyedEarth;

	// game over states
	private Image currentGameOver, gameOver, yes, no, neither;

	// spaceship flame images
	private Image rightOrangeFlame, leftOrangeFlame, downOrangeFlame;

	private Image explosion1, explosion2;

	private Graphics second;
	private URL base;
	private Animation anim, rightShipAnim, downShipAnim, leftShipAnim,
			deathAnim;

	// right facing enemies
	public static ArrayList<Spaceship> rightEnemies = new ArrayList<Spaceship>();

	// left facing enemies
	public static ArrayList<Spaceship> leftEnemies = new ArrayList<Spaceship>();

	// downward facing enemies
	public static ArrayList<Spaceship> downEnemies = new ArrayList<Spaceship>();

	// right facing lasers
	public static ArrayList<Spaceship> rightLasers = new ArrayList<Spaceship>();

	// left facing lasers
	public static ArrayList<Spaceship> leftLasers = new ArrayList<Spaceship>();

	// downward facing lasers
	public static ArrayList<Spaceship> downLasers = new ArrayList<Spaceship>();

	// lasers from laser gun
	private ArrayList<Projectile> projectiles;

	// timing for threads
	
	private int[] times = new int[5];

	private static Background bg1, bg2;

	public static int currentIndex;

	@Override
	public void init() {

		setSize(480, 800);
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);
		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("Imminent Doom");
		try {
			base = getDocumentBase();
		} catch (Exception e) {
			System.out.println(e);
			System.exit(0);
		}

		// Image Setups

		earth = getImage(base, "data/Earth.png");
		destroyedEarth = getImage(base, "data/BOOM.png");

		laserGunImage = getImage(base, "data/LaserGun3.png");

		spaceshipDown = getImage(base, "data/spaceship1.png");
		spaceshipRight = getImage(base, "data/spaceship2.png");
		spaceshipLeft = getImage(base, "data/spaceship3.png");

		rightOrangeFlame = getImage(base, "data/RightShipOrangeFlame.png");
		leftOrangeFlame = getImage(base, "data/LeftShipOrangeFlame.png");
		downOrangeFlame = getImage(base, "data/DownShipOrangeFlame.png");

		explosion1 = getImage(base, "data/BOOM.png");
		explosion2 = getImage(base, "data/BANG.png");

		laser1 = getImage(base, "data/laser1.png");
		laser2 = getImage(base, "data/laser2.png");
		laser3 = getImage(base, "data/laser3.png");

		background = getImage(base, "data/background.png");

		gameOver = getImage(base, "data/gg.png");
		yes = getImage(base, "data/RestartYes.png");
		no = getImage(base, "data/RestartNo.png");
		neither = getImage(base, "data/RestartNone.png");

		anim = new Animation();
		anim.addFrame(laserGunImage, 1250);

		rightShipAnim = new Animation();
		rightShipAnimation();

		leftShipAnim = new Animation();
		leftShipAnimation();

		downShipAnim = new Animation();
		downShipAnimation();

		deathAnim = new Animation();
		deathAnimation();

		currentLaserGun = anim.getImage();
	}

	private void rightShipAnimation() {
		rightShipAnim.addFrame(spaceshipRight, 10000);
		rightShipAnim.addFrame(rightOrangeFlame, 1000);
	}

	private void leftShipAnimation() {
		leftShipAnim.addFrame(spaceshipLeft, 10000);
		leftShipAnim.addFrame(leftOrangeFlame, 1000);
	}

	private void downShipAnimation() {
		downShipAnim.addFrame(spaceshipDown, 10000);
		downShipAnim.addFrame(downOrangeFlame, 1000);
	}

	private void deathAnimation() {
		deathAnim.addFrame(explosion1, 1000);
		deathAnim.addFrame(explosion2, 1000);
	}

	@Override
	public void start() {

		currentGameOver = neither;
		bg1 = new Background(0, 0);
		bg2 = new Background(-480, 0); // (-480, 0)
		laserGun = new LaserGun();

		for(int i = 0; i < times.length; i++) {
			times[i] = i * 1000;
		}
		
		Thread thread = new Thread(this);
		thread.start();

		Thread enemySpawner1 = new Thread(new Runnable() {
			public void run() {
				while (state == GameState.Running) {
					createRightShips();
					createLeftSideShips();
				}
			}
		});
		enemySpawner1.start();

		Thread enemySpawner2 = new Thread(new Runnable() {
			public void run() {
				while (state == GameState.Running) {
					createLeftShips();
					createRightSideShips();
				}
			}
		});
		enemySpawner2.start();

		Thread enemySpawner3 = new Thread(new Runnable() {
			public void run() {
				while (state == GameState.Running) {
					createDownShips();
				}
			}
		});
		enemySpawner3.start();
	}

	@Override
	public void stop() {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void run() {
		int frame = 1;
		if (state == GameState.Running) {
			
			while (state == GameState.Running) {
				
				if (health <= 0) {
					state = GameState.Dead;
				}
				
				System.out.println("I am running now " + frame);
				frame++;

				laserGun.update();
				currentLaserGun = anim.getImage();

				projectiles = laserGun.getProjectiles();
				for (int i = 0; i < projectiles.size(); i++) {
					Projectile p = (Projectile) projectiles.get(i);
					if (p.isVisible() == true) {
						p.update();
					} else {
						projectiles.remove(i);
					}
					if (projectiles.size() > 1) {
					}
				}

				// right ship movement
				for (currentIndex = 0; currentIndex < rightEnemies.size(); currentIndex++) {
					rightEnemies.get(currentIndex).update();
				}

				// left ship movement
				for (currentIndex = 0; currentIndex < leftEnemies.size(); currentIndex++) {
					leftEnemies.get(currentIndex).update();
				}

				// down ship movement
				for (currentIndex = 0; currentIndex < downEnemies.size(); currentIndex++) {
					downEnemies.get(currentIndex).update();
				}

				// corresponding laser updates (moving them)
				for (currentIndex = 0; currentIndex < rightLasers.size(); currentIndex++) {
					rightLasers.get(currentIndex).laserUpdate1();
				}

				for (currentIndex = 0; currentIndex < leftLasers.size(); currentIndex++) {
					leftLasers.get(currentIndex).laserUpdate2();
				}

				for (currentIndex = 0; currentIndex < downLasers.size(); currentIndex++) {
					downLasers.get(currentIndex).laserUpdate3();
				}

				bg1.update();
				bg2.update();
				animate();
				repaint();

				// creates changes in health bar
				for (int i = 0; i < rightEnemies.size(); i++) {
					if (!rightEnemies.get(i).isMovingX()) {
						if (!rightEnemies.get(i).isMovingY()) {
							health -= .1;
						}
					}
				}

				for (int i = 0; i < leftEnemies.size(); i++) {
					if (!leftEnemies.get(i).isMovingX()) {
						if (!leftEnemies.get(i).isMovingY()) {
							health -= .1;
						}
					}
				}

				for (int i = 0; i < downEnemies.size(); i++) {
					if (!downEnemies.get(i).isMovingY()) {
						health -= .1;
					}
				}

				try {
					Thread.sleep(17);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void animate() {
		// anim.update(60);
		rightShipAnim.update(50);
		leftShipAnim.update(50);
		downShipAnim.update(50);
		deathAnim.update(50);
	}

	@Override
	public void update(Graphics g) {
		if (image == null) {
			image = createImage(this.getWidth(), this.getHeight());
			second = image.getGraphics();
		}

		second.setColor(getBackground());
		second.fillRect(0, 0, getWidth(), getHeight());
		second.setColor(getForeground());
		paint(second);

		g.drawImage(image, 0, 0, this);

	}

	@Override
	public void paint(Graphics g) {

		if (state == GameState.Running) {

			g.drawImage(background, (int) bg1.getBgX(), (int) bg1.getBgY(),
					this);
			g.drawImage(background, (int) bg2.getBgX(), (int) bg2.getBgY(),
					this);

			if (health > 0) {
				g.drawImage(earth, 165, 380, this);
			} else {
				g.drawImage(destroyedEarth, 160, 380, this);
			}

			projectiles = laserGun.getProjectiles();
			for (int currentIndex = 0; currentIndex < projectiles.size(); currentIndex++) {
				Projectile p = (Projectile) projectiles.get(currentIndex);
				g.setColor(Color.MAGENTA);
				g.fillRect(p.getX(), p.getY(), 5, 50);
			}

			g.drawImage(currentLaserGun, laserGun.getCenterX() - 61,
					laserGun.getCenterY() - 63, this);

			// Right spaceship laser begins firing here
			for (int i = 0; i < rightLasers.size(); i++) {
				if (!rightEnemies.get(i).isMovingX()) {
					if (!rightEnemies.get(i).isMovingY()) {
						g.drawImage(laser2, (int) rightLasers.get(i)
								.getCenterX() - 45, (int) rightLasers.get(i)
								.getCenterY() + 25, this);
					}
				}
			}

			// Left spaceship laser begins firing here
			for (int i = 0; i < leftLasers.size(); i++) {
				if (!leftEnemies.get(i).isMovingX()) {
					if (!leftEnemies.get(i).isMovingY()) {
						g.drawImage(laser3, (int) leftLasers.get(i)
								.getCenterX() - 45, (int) leftLasers.get(i)
								.getCenterY() + 25, this);
					}
				}
			}

			// Down spaceship laser begins firing here
			for (int i = 0; i < downLasers.size(); i++) {
				if (!downEnemies.get(i).isMovingY()) {
					g.drawImage(laser1,
							(int) downLasers.get(i).getCenterX() - 45,
							(int) downLasers.get(i).getCenterY() + 25, this);
					// System.out.println(i + " " + lasers.get(i).getCenterY());
				}
			}

			// Right ship
			for (int i = 0; i < rightEnemies.size(); i++) {
				g.drawImage(rightShipAnim.getImage(), (int) rightEnemies.get(i)
						.getCenterX() - 48, (int) rightEnemies.get(i)
						.getCenterY() - 48, this);
			}

			// Left ship
			for (int i = 0; i < leftEnemies.size(); i++) {
				g.drawImage(leftShipAnim.getImage(), (int) leftEnemies.get(i)
						.getCenterX() - 48, (int) leftEnemies.get(i)
						.getCenterY() - 48, this);
			}

			// Down ship
			for (int i = 0; i < downEnemies.size(); i++) {
				if (!downEnemies.get(i).isDead()) {
					g.drawImage(downShipAnim.getImage(),
							(int) downEnemies.get(i).getCenterX() - 48,
							(int) downEnemies.get(i).getCenterY() - 48, this);
				} else {
					/*
					 * g.drawImage(explosion1, (int) downEnemies.get(i)
					 * .getCenterX() - 48, (int) downEnemies.get(i)
					 * .getCenterY() - 48, this); g.drawImage(explosion2, (int)
					 * downEnemies.get(i) .getCenterX() - 48, (int)
					 * downEnemies.get(i) .getCenterY() - 48, this);
					 */
					StartingClass.downEnemies.remove(i);
				}
			}

			g.setFont(font);
			g.setColor(Color.WHITE);
			g.drawString(Integer.toString(score), 300, 740);

			// paints health bar
			g.setColor(Color.WHITE);
			g.fillRect(300, 750, 154, 29);
			if (health <= 50) {
				g.setColor(Color.RED);
			} else if (health <= 100) {
				g.setColor(Color.YELLOW);
			} else {
				g.setColor(Color.GREEN);
			}

			g.fillRect(302, 752, (int) health, 25);

			// TESTING FOR HIT BOX

			/*
			 * g.setColor(Color.WHITE); for (int i = 0; i < leftEnemies.size();
			 * i++) { g.fillRect((int) leftEnemies.get(i).getCenterX() - 40,
			 * (int) leftEnemies.get(i).getCenterY() - 20, 10, 60); }
			 * g.setColor(Color.YELLOW); for (int i = 0; i < leftEnemies.size();
			 * i++) { g.fillRect((int) leftEnemies.get(i).getCenterX() - 30,
			 * (int) leftEnemies.get(i).getCenterY() - 25, 10, 60); }
			 * g.setColor(Color.BLUE); for (int i = 0; i < leftEnemies.size();
			 * i++) { g.fillRect((int) leftEnemies.get(i).getCenterX() - 20,
			 * (int) leftEnemies.get(i).getCenterY() - 30, 10, 60); }
			 * g.setColor(Color.RED); for (int i = 0; i < leftEnemies.size();
			 * i++) { g.fillRect((int) leftEnemies.get(i).getCenterX() - 10,
			 * (int) leftEnemies.get(i).getCenterY() - 40, 10, 60); }
			 * g.setColor(Color.GREEN); for (int i = 0; i < leftEnemies.size();
			 * i++) { g.fillRect((int) leftEnemies.get(i).getCenterX(), (int)
			 * leftEnemies.get(i).getCenterY() - 50, 10, 60); }
			 * g.setColor(Color.ORANGE); for (int i = 0; i < leftEnemies.size();
			 * i++) { g.fillRect((int) leftEnemies.get(i).getCenterX() + 10,
			 * (int) leftEnemies.get(i).getCenterY() - 60, 10, 60); }
			 */

		}

		// Player has lost
		else if (state == GameState.Dead) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 800, 480);
			g.drawImage(background, 0, 0, this);
			g.drawImage(gameOver, 50, 0, this);
			g.drawImage(currentGameOver, 50, 500, this);
			
			System.out.println("lost");
			repaint();
		}
		
		else {
			g.setColor(Color.PINK);
			g.fillRect(0, 0, 480, 800);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			if (state == GameState.Running) {
				laserGun.moveLeft();
				laserGun.setMovingLeft(true);
			} else {
				currentGameOver = yes;
			}
			break;

		case KeyEvent.VK_RIGHT:
			if (state == GameState.Running) {
				laserGun.moveRight();
				laserGun.setMovingRight(true);
			} else {
				currentGameOver = no;
			}
			break;

		case KeyEvent.VK_SPACE:
			if (laserGun.isReadyToFire()) {
				if (state == GameState.Running) {
					laserGun.shoot();
					laserGun.setReadyToFire(false);
				}
				break;
			}
		case KeyEvent.VK_ENTER:
			if (state == GameState.Dead) {
				if (currentGameOver == yes) {
					restartGame();
				}
				else {
					state = GameState.MainMenu;
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			if (state == GameState.Running) {
				laserGun.stopLeft();
				System.out.println("left");
			}
			break;

		case KeyEvent.VK_RIGHT:
			if (state == GameState.Running) {
				laserGun.stopRight();
			}
			break;

		case KeyEvent.VK_SPACE:
			if (state == GameState.Running) {
				// makes it so player can't fire a "stream" of lasers
				if (projectiles.get(projectiles.size() - 1).getY() + 150 < laserGun
						.getCenterY()) {
					laserGun.setReadyToFire(true);
				}
			}
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// Auto-generated method stub

	}
	
	private void restartGame() {
		//TODO
		
		state = GameState.Running;
		
		rightEnemies.clear();
		leftEnemies.clear();
		downEnemies.clear();
		rightLasers.clear();
		leftLasers.clear();
		downLasers.clear();
		projectiles.clear();
		
		score = 0;
		health = 150;
		
		for(int i = 0; i < times.length; i++) {
			times[i] = 0;
		}
		
		init();
		start();
		run();
		animate();
	}

	private void createRightShips() {
		Random generator = new Random();

		int randomNumber = generator.nextInt(150) + 1;

		spaceships = new Spaceship(randomNumber, -80);
		rightEnemies.add(spaceships);

		laserRight = new Spaceship(MIDDLE_X, MIDDLE_Y);

		rightLasers.add(laserRight);

		try {
			Thread.sleep(times[1]);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		int randomTime = generator.nextInt((100 - 50) + 1) + 50;

		times[1] -= randomTime;

		if (times[1] < 1000) {
			times[1] = 1000;
		}
	}

	private void createLeftSideShips() {
		Random generator = new Random();

		int randomNumber = generator.nextInt(200) + 1;

		spaceships = new Spaceship(-50, randomNumber);
		rightEnemies.add(spaceships);

		laserRight = new Spaceship(MIDDLE_X, MIDDLE_Y);

		rightLasers.add(laserRight);

		try {
			Thread.sleep(times[4]);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		int randomTime = generator.nextInt((100 - 50) + 1) + 50;

		times[4] -= randomTime;

		if (times[4] < 1000) {
			times[4] = 1000;
		}
	}

	private void createLeftShips() {
		Random generator = new Random();

		int randomNumber = generator.nextInt((400 - 310) + 1) + 310;
		spaceships2 = new Spaceship(randomNumber, -80);
		leftEnemies.add(spaceships2);

		laserLeft = new Spaceship(MIDDLE_X, MIDDLE_Y);
		leftLasers.add(laserLeft);

		try {
			Thread.sleep(times[2]);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int randomTime = generator.nextInt((100 - 50) + 1) + 50;

		times[2] -= randomTime;

		if (times[2] < 1000) {
			times[2] = 1000;
		}
	}

	private void createRightSideShips() {
		Random generator = new Random();

		int randomNumber = generator.nextInt(200) + 1;

		spaceships = new Spaceship(550, randomNumber);
		leftEnemies.add(spaceships);

		laserLeft = new Spaceship(MIDDLE_X, MIDDLE_Y);

		leftLasers.add(laserLeft);

		try {
			Thread.sleep(times[5]);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		int randomTime = generator.nextInt((100 - 50) + 1) + 50;

		times[5] -= randomTime;

		if (times[5] < 1000) {
			times[5] = 1000;
		}
	}

	private void createDownShips() {
		Random generator = new Random();

		int randomNumber = generator.nextInt((300 - 250) + 1) + 250;
		spaceships3 = new Spaceship(randomNumber, -80);
		downEnemies.add(spaceships3);

		laserDown = new Spaceship(MIDDLE_X, MIDDLE_Y);

		downLasers.add(laserDown);

		try {
			Thread.sleep(times[3]);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int randomTime = generator.nextInt((100 - 50) + 1) + 50;

		times[3] -= randomTime;

		if (times[3] < 1000) {
			times[3] = 1000;
		}
	}

	private void shipDeath() {
		Thread dieShip = new Thread(new Runnable() {
			public void run() {
				// TODO: death animation
			}
		});
		dieShip.start();
	}

	public static Background getBg1() {
		return bg1;
	}

	public static Background getBg2() {
		return bg2;
	}

	public static LaserGun getLaserGun() {
		return laserGun;
	}
}