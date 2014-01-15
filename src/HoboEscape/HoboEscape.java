package HoboEscape;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class HoboEscape extends Canvas implements MouseMotionListener,
		KeyListener, MouseListener {

	private static int mouseX, mouseY;
	public static boolean mouse = false;
	public static Room room;
	public static boolean[] keyCodes, keyChars;
	private static boolean initialized = false;
	private static double GraphicalLoadingBar = 0;
	private static int totalToLoad = 0, loaded = 0;

	public HoboEscape() {

		// do initialization stuff for the window... ew
		setSize(Main.WIDTH, Main.HEIGHT);
		JFrame frame = new JFrame();
		frame.setResizable(false);
		frame.setVisible(true);
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(frame.getPreferredSize());
		frame.setLocationRelativeTo(null);
		addKeyListener(this);
		addMouseMotionListener(this);
		addMouseListener(this);
		// shut up, it works, i don't care if it makes no sense.

		new Thread(new Runnable() {
			public void run() {

				Variable.setBaseDir(Main.BASE_DIR);

				keyCodes = new boolean[512];
				for (int i = 0; i < keyCodes.length; i++) {
					keyCodes[i] = false;
				}
				keyChars = new boolean[512];
				for (int i = 0; i < keyChars.length; i++) {
					keyChars[i] = false;
				}

				new Images();

				// initializes the rooms static objects...
				new SplashScreen();
				new Menu();
				new Levels();
				new GameRoom();
				setRoom(SplashScreen.staticObject);

				// setRoom(GameRoom.staticObject);
				// GameRoom.setLevel(new Level(1));
				initialized = true;

			}
		}).start();
		while (!initialized) {
			try {
				Thread.sleep(100);
			} catch (Exception e) {

			}
			repaint();
		}
		while (/* !(Display.isCloseRequested()) */true) {
			tick();
			repaint();
			try {
				Thread.sleep(17);
			} catch (Exception e) {

			}
			// Display.update();
			// Display.sync(30);
		}
		// System.exit(0);
	}

	private void tick() {
		room.tick();
	}

	public void update(Graphics g) {
		Image image = (Image) (new BufferedImage(Main.WIDTH, Main.HEIGHT, BufferedImage.TYPE_INT_ARGB));
		paint(image.getGraphics());
		g.drawImage(image, 0, 0, null);

	}

	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Main.BACKGROUND_COLOR);
		g2d.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawImage(Images.titleScreenBackground, 0, 0, null);

		// System.out.println(room);
		try {
			room.paint(g2d);
			// System.out.println(room);
		} catch (Exception e) {
			//the underlying background is currently the non splash background. so before the
			// splash loads up, the normal color is shown.
			// here, we change that.
			
			g2d.setColor(Main.FOREGROUND_COLOR);
			g2d.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
			
			
			// this is expected to happen when we create the window considering
			// room is null...

			// because the first frame the only thing drawn
			// is the blue background...

			g2d.drawImage(Images.splashBackground, 0, 0, null);
			g2d.setFont(Main.BASE_FONT);
			int border = 10;
			String loading = "Loading";
			int loopTime = 1000;
			int dotsInLoop = 4;
			int dots = ((int)(System.currentTimeMillis() % loopTime))/(loopTime/(dotsInLoop + 1));
			for(int i = 0; i < dots; i ++) {
				loading+=".";
			}
			g2d.setColor(Main.FONT_COLOR);
			g2d.drawString(loading, border, Main.HEIGHT - border);
			
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		room.keyPressed(e);
		keyCodes[(int) e.getKeyCode()] = true;
		keyChars[(int) e.getKeyChar()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keyCodes[(int) e.getKeyCode()] = false;
		keyChars[(int) e.getKeyChar()] = false;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		if (Main.debug > 1)
			System.out.println("mouseX: " + mouseX + "\nmouseY: " + mouseY);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		if (Main.debug > 1)
			System.out.println("mouseX: " + mouseX + "\nmouseY: " + mouseY);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		mouse = true;
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		mouse = false;
	}

	public static int getMouseX(Room perspective) {
		return mouseX - perspective.getX();
	}

	public static int getMouseY(Room perspective) {
		return mouseY - perspective.getY();
	}

	/**
	 * pass null for transition if you want it to just cut to the next room.
	 * 
	 * @param room
	 * @param transition
	 */
	public static void setRoom(Room room, String transition) {
		if (transition == null) {
			HoboEscape.room = room;
		} else {
			HoboEscape.room = Transition.getTransition(transition,
					HoboEscape.room, room);
		}
	}

	/**
	 * pass null for transition if you want it to just cut to the next room.
	 * 
	 * @param room
	 * @param transition
	 */
	public static void setRoom(Room room) {
		setRoom(room, null);
	}
}
