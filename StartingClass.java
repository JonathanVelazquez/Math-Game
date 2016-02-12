
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

/**
 * This class is the main that takes all the different classes and puts them
 * together to actually run the game.
 * 
 * 
 * Code that I am honestly unsure of what it really does or how it does it has
 * the comment "Idk what this does" if you want to ctrl + f those
 */
public class StartingClass extends Applet implements Runnable, KeyListener {

	public static final int MIDDLE_X = 262; // approximate middle x value
	public static final int MIDDLE_Y = 415; // approximate middle y value

	// 3 states of the game
	enum GameState {
		Running, Dead, MainMenu
	}

	GameState state = GameState.Running;

	private static LaserGun laserGun;

	// Lasers and spaceships are both listed as enemies because they are
	// coupled.
	public static Spaceship spaceships3, spaceship1, laser, laserDown;

	public static int score = 0;
	public static double health = 150; // should begin at 150
	private String equation = "xxxxx";
	private static int number1, number2;
	public static boolean readyForNewEquation = true;

	// Only used for write the score
	private Font font = new Font(null, Font.BOLD, 30);

	private Image image, background;

	// Pictures of enemies
	private Image spaceshipDown, laser1;

	// Pictures of Laser Gun
	private Image currentLaserGun, laserGunImage;

	private Image earth, destroyedEarth;

	// game over states
	private Image currentGameOver, gameOver, yes, no, neither;

	// spaceship flame images
	private Image downOrangeFlame;

	// Explosion of ships
	private Image explosion1, explosion2;

	// necessary for graphics
	private Graphics second;
	private URL base;

	// Animations for space ships
	private Animation anim, downShipAnim, deathAnim;

	// spaceships
	public static ArrayList<Spaceship> downEnemies = new ArrayList<Spaceship>();

	// lasers from spaceships
	public static ArrayList<Spaceship> downLasers = new ArrayList<Spaceship>();

	// lasers from laser gun
	private ArrayList<Projectile> projectiles;

	// Used to scroll background
	private static Background bg1, bg2;

	public static int currentIndex;

	/**
	 * This method initializes all of the objects and images that will be in the
	 * game. Read the actual Java documentation for this method for more
	 * information.
	 */
	@Override
	public void init() {

		// Size of screen the game is in
		setSize(480, 800);
		setBackground(Color.BLACK);

		// Idk what this does
		setFocusable(true);
		addKeyListener(this);

		Frame frame = (Frame) this.getParent().getParent();

		// Title
		frame.setTitle("MATHS");

		try {

			// Idk what this does
			base = getDocumentBase();
		} catch (Exception e) {
			System.out.println(e);
			System.exit(0);
		}

		// Image Setups
		background = getImage(base, "data/background.png");

		earth = getImage(base, "data/Earth.png");

		laserGunImage = getImage(base, "data/LaserGun3.png");

		spaceshipDown = getImage(base, "data/spaceship1.png");

		downOrangeFlame = getImage(base, "data/DownShipOrangeFlame.png");

		explosion1 = getImage(base, "data/BOOM.png");
		explosion2 = getImage(base, "data/BANG.png");

		laser1 = getImage(base, "data/laser1.png");

		gameOver = getImage(base, "data/gg.png");
		yes = getImage(base, "data/RestartYes.png");
		no = getImage(base, "data/RestartNo.png");
		neither = getImage(base, "data/RestartNone.png");

		anim = new Animation();
		anim.addFrame(laserGunImage, 1250);

		// Animation for ships
		downShipAnim = new Animation();
		downShipAnimation();

		deathAnim = new Animation();
		deathAnimation();

		currentLaserGun = anim.getImage();
	}

	/**
	 * Animation for ships
	 */
	private void downShipAnimation() {
		downShipAnim.addFrame(spaceshipDown, 10000);
		downShipAnim.addFrame(downOrangeFlame, 1000);
	}

	/**
	 * Death animation for ships
	 */
	private void deathAnimation() {
		deathAnim.addFrame(explosion1, 1000);
		deathAnim.addFrame(explosion2, 1000);
	}

	/**
	 * Starts each thread in the game. Read the actual Java documentation for
	 * this method for more information.
	 */
	@Override
	public void start() {

		currentGameOver = yes;

		// places background
		bg1 = new Background(0, 0);
		bg2 = new Background(-480, 0); // Should be (-480, 0)

		laserGun = new LaserGun();

		// Creates and starts a new Thread that is the game
		Thread thread = new Thread(this);
		thread.start();

		createDownShips();
	}

	/*
	 * I never actually used these 2 methods. They are part of the Applet class
	 */
	@Override
	public void stop() {
	}

	@Override
	public void destroy() {
	}

	/**
	 * This method actually does most of the work. It's what is being run in the
	 * main thread. Read the actual Java documentation for this method for more
	 * information.
	 */
	@Override
	public void run() {
		int frame = 1; // used for testing

		while (state == GameState.Running) {

			if (health <= 0) {
				state = GameState.Dead;
			}

			if(downEnemies.size() == 1) {
				createDownShips();
			}
			// used for testing
			 System.out.println("I am running now " + frame);
			frame++;

			// Moves the laser gun
			laserGun.update();
			currentLaserGun = anim.getImage();

			// List of projectiles that have been shot from the laser gun
			projectiles = laserGun.getProjectiles();
			for (int i = 0; i < projectiles.size(); i++) {

				// 1 individual laser
				Projectile p = (Projectile) projectiles.get(i);

				// As long as the projectile has not reached the edge of the
				// screen it's position should be moved
				if (p.isVisible() == true) {
					p.update();
				} else {
					projectiles.remove(i);
				}
			}

			// down ship movement
			for (currentIndex = 0; currentIndex < downEnemies.size(); currentIndex++) {
				downEnemies.get(currentIndex).update();
			}

			// corresponding laser updates (moving them)

			for (currentIndex = 0; currentIndex < downLasers.size(); currentIndex++) {
				downLasers.get(currentIndex).laserUpdate3();
			}

			// background movement
			bg1.update();
			bg2.update();

			// handles animations
			animate();

			// Not exactly sure what this does but I think it continues to show
			// images to the screen frame after frame
			repaint();

			/*
			 * The next 3 for loops cause player's health bar to decrease
			 */

			for (int i = 0; i < downEnemies.size(); i++) {
				if (!downEnemies.get(i).isMovingY()) {
					health -= .1;
				}
			}

			// pauses the current thread for 17 milliseconds (for 60 fps) in
			// order to update for the next frame (I think)
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Handles animations for each image
	 */
	public void animate() {
		// anim.update(60);
		downShipAnim.update(50);
		deathAnim.update(50);
	}

	/**
	 * Idk what this method really does actually. Read the actual Java
	 * documentation for this method for more information.
	 * 
	 * @param g
	 *            a Graphics object
	 */
	@Override
	public void update(Graphics g) {

		// Idk what this does
		if (image == null) {
			image = createImage(this.getWidth(), this.getHeight());
			second = image.getGraphics();
		}

		// Idk what this does
		second.setColor(getBackground());
		second.fillRect(0, 0, getWidth(), getHeight());
		second.setColor(getForeground());
		paint(second);

		g.drawImage(image, 0, 0, this);

	}

	/**
	 * Displays the images onto the game screen. Read the actual Java
	 * documentation for this method for more information.
	 * 
	 * @param g
	 *            a Graphics object
	 */
	@Override
	public void paint(Graphics g) {

		// Don't display anything if the game is not being played currently
		if (state == GameState.Running) {

			// displays the background image
			g.drawImage(background, (int) bg1.getBgX(), (int) bg1.getBgY(), this);
			g.drawImage(background, (int) bg2.getBgX(), (int) bg2.getBgY(), this);

			// displays the health bar
			if (health > 0) {
				g.drawImage(earth, 165, 380, this);
			} else {
				g.drawImage(destroyedEarth, 160, 380, this);
			}

			// displays the lasers from the laser gun
			projectiles = laserGun.getProjectiles();
			for (currentIndex = 0; currentIndex < projectiles.size(); currentIndex++) {
				Projectile p = (Projectile) projectiles.get(currentIndex);
				g.setColor(Color.MAGENTA);

				// size of laser
				g.fillRect(p.getX(), p.getY(), 5, 50);
			}

			// displays the laser gun at the bottom of the screen
			g.drawImage(currentLaserGun, laserGun.getCenterX() - 61, laserGun.getCenterY() - 63, this);

			// displays spaceship laser once it reaches appropriate firing
			// distance from the earth
			for (int i = 0; i < downLasers.size(); i++) {
				if (!downEnemies.get(i).isMovingY()) {
					g.drawImage(laser1, (int) downLasers.get(i).getCenterX() - 45,
							(int) downLasers.get(i).getCenterY() + 25, this);
					// System.out.println(i + " " + lasers.get(i).getCenterY());
				}
			}

			// displays down ship
			for (int i = 0; i < downEnemies.size(); i++) {
				if (!downEnemies.get(i).isDead()) {
					g.drawImage(downShipAnim.getImage(), (int) downEnemies.get(i).getCenterX() - 48,
							(int) downEnemies.get(i).getCenterY() - 48, this);

					g.setFont(font);
					g.setColor(Color.CYAN);
					g.drawString(Integer.toString(downEnemies.get(i).getNumber()),
							(int) downEnemies.get(i).getCenterX() - 35, (int) downEnemies.get(i).getCenterY() - 35);
				} else {
					/*
					 * trying to get death animation to work here
					 * 
					 * 
					 * g.drawImage(explosion1, (int) downEnemies.get(i)
					 * .getCenterX() - 48, (int) downEnemies.get(i)
					 * .getCenterY() - 48, this); g.drawImage(explosion2, (int)
					 * downEnemies.get(i) .getCenterX() - 48, (int)
					 * downEnemies.get(i) .getCenterY() - 48, this);
					 */
					StartingClass.downEnemies.remove(i);
				}
			}

			// writes the current score
			g.setFont(font);
			g.setColor(Color.WHITE);
			g.drawString(Integer.toString(score), 300, 740);

			g.setColor(Color.CYAN);

			// Displays the problem to be solved
			if (readyForNewEquation) {
				equation = getEquation();
			}
			g.drawString(equation, 50, 740);

			// displays health bar
			g.setColor(Color.WHITE);
			g.fillRect(300, 750, 154, 29);

			// Changes health bar color based on amount of health
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

			// System.out.println("lost");
			repaint();
		}

		// This will be the main menu
		else {
			g.setColor(Color.PINK);
			g.fillRect(0, 0, 480, 800);
		}
	}

	/**
	 * Handles actions when keys are pressed on the keyboard. Left, right,
	 * space, and enter.
	 */
	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {

		// move left
		case KeyEvent.VK_LEFT:
			if (state == GameState.Running) {
				laserGun.moveLeft();
				laserGun.setMovingLeft(true);
			} else {
				currentGameOver = yes;
			}
			break;

		// move right
		case KeyEvent.VK_RIGHT:
			if (state == GameState.Running) {
				laserGun.moveRight();
				laserGun.setMovingRight(true);
			} else {
				currentGameOver = no;
			}
			break;

		// shoot
		case KeyEvent.VK_SPACE:
			if (laserGun.isReadyToFire()) {
				if (state == GameState.Running) {
					laserGun.shoot();

					// makes it so player can't fire a "stream" of lasers.
					// Must release the space bar before firing again
					laserGun.setReadyToFire(false);
				}
				break;
			}

			// Only usable when the game is over
		case KeyEvent.VK_ENTER:
			if (state == GameState.Dead) {
				if (currentGameOver == yes) {
					restartGame();
				} else {
					state = GameState.MainMenu;
				}
			}
		}
	}

	/**
	 * Handles events when keys on the keyboard are released
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {

		// stop moving left
		case KeyEvent.VK_LEFT:
			if (state == GameState.Running) {
				laserGun.stopLeft();
			}
			break;

		// stop moving right
		case KeyEvent.VK_RIGHT:
			if (state == GameState.Running) {
				laserGun.stopRight();
			}
			break;

		// stop shooting (but why would you really want to do that?!)
		case KeyEvent.VK_SPACE:
			if (state == GameState.Running) {
				// makes it so player can't fire a "stream" of lasers
				if (projectiles.size() > 1) {
					if (projectiles.get(projectiles.size() - 1).getY() + 150 < laserGun.getCenterY()) {
						laserGun.setReadyToFire(true);
					}
				} else {
					laserGun.setReadyToFire(true);
				}
			}
			break;
		}
	}

	// This is part of the KeyListener interface. Not necessary to use
	@Override
	public void keyTyped(KeyEvent e) {
	}

	/**
	 * Restarts the game to it's original state and recalls necessary methods to
	 * start the game. Health = 150, score = 0, and no enemies have spawned.
	 * 
	 * UNFINISHED
	 */
	private void restartGame() {
		// TODO

		state = GameState.Running;

		downEnemies.clear();
		downLasers.clear();
		projectiles.clear();

		score = 0;
		health = 150;
		
		init();
		start();
		run();
		animate();
	}

	/**
	 * Spawns space ships
	 */
	private void createDownShips() {

		// creates 5 ships
		for (int i = 0; i < 5; i++) {
			downEnemies.add(new Spaceship((100 * i) + 65, -80));

			// creates corresponding lasers
			downLasers.add(new Spaceship(MIDDLE_X, MIDDLE_Y));
		}
	}

	/**
	 * Death animation of ships.
	 * 
	 * UNFINISHED
	 */
	private void shipDeath() {
		Thread dieShip = new Thread(new Runnable() {
			/**
			 * Runs the current Thread
			 */
			public void run() {
				// TODO: death animation
			}
		});
		dieShip.start();
	}

	/**
	 * Creates the equation to be solved based on the numbers provided by the
	 * crows
	 * 
	 * @return the equation as a String
	 */
	private String getEquation() {
		Random generator = new Random();

		// picks a random enemy
		int randomEnemyIndex = generator.nextInt(downEnemies.size());
		number1 = generator.nextInt(downEnemies.get(randomEnemyIndex).getNumber());
		number2 = downEnemies.get(randomEnemyIndex).getNumber() - number1;

		readyForNewEquation = false;
		return number1 + " + " + number2 + " = ?";
	}

	/**
	 * Accessor method for first background image
	 * 
	 * @return the first background image
	 */
	public static Background getBg1() {
		return bg1;
	}

	/**
	 * Accessor method for second background image
	 * 
	 * @return the second background image
	 */
	public static Background getBg2() {
		return bg2;
	}

	/**
	 * Accessor method for laser gun object
	 * 
	 * @return the laser gun object
	 */
	public static LaserGun getLaserGun() {
		return laserGun;
	}

	public int getNumber1() {
		return number1;
	}

	public int getNumber2() {
		return number2;
	}
	
	public double getHealth() {
		return health;
	}
	 
}