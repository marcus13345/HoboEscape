package HoboEscape;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
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
import java.io.File;
import java.util.Stack;

import javax.swing.JFrame;

/**
 * this is the main hobo escape class. called thusly because originally this was going to be a platformer
 * in which you are a hobo running away from the police. this class takes care of a few things pertaining
 * mostly to the managing of rooms which are the building block of the game, like a scene. as well
 * it is responsible for managing the main JFrame/Canvas.
 * @author mgosselin
 *
 */
public class HoboEscape extends Canvas implements MouseMotionListener, KeyListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int mouseX, mouseY;
	public static boolean mouse = false;
	public static Stack<Room> room;
	public static boolean[] keyCodes, keyChars;
	private static boolean initialized = false;
	
	/**
	 * THIS is a FINAL. it cannot be declared thusly because lord knows i cant make constants
	 * at runtime. but it is. it is a constant.
	 */
	public static File thisJar;

	static {
		try{
			thisJar = new File(HoboEscape.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
		}catch(Exception e) {
		}
		Variable.setBaseDir("" + thisJar.getParent());
	}
	
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

		room = new Stack<Room>();
		
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

				pushRoom(Room.SPLASH_SCREEN, null);

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
		while (true) {
			tick();
			
			repaint();
			try {
				Thread.sleep(17);
			} catch (Exception e) {

			}
		}
	}

	private void tick() {
		room.peek().tick();
	}

	public void update(Graphics g) {
		Image image = (Image) (new BufferedImage(Main.WIDTH, Main.HEIGHT, BufferedImage.TYPE_INT_ARGB));
		paint(image.getGraphics());
		g.drawImage(image, 0, 0, null);

	}

	public void paint(Graphics g) {
		System.out.println();
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Main.BACKGROUND_COLOR);
		g2d.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawImage(Images.titleScreenBackground, 0, 0, null);

		try {
			room.peek().paint(g2d);
			int size = room.size();
			int i = 0;
			String str = "";
			for(Room r : room) {
				str += "" + r.toString() + (i != size - 1 ? " -> " : "");
				i ++;
			}
			g.setFont(new Font("Arial", Font.BOLD, 20));
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, Main.WIDTH, 30);
			g.setColor(Color.BLACK);
			g.drawString(str, 10, 21);
		} catch (Exception e) {
			// the underlying background is currently the non splash background.
			// so before the
			// splash loads up, the normal color is shown.
			// here, we change that.

			g2d.setColor(Main.BACKGROUND_COLOR);
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
			int dots = ((int) (System.currentTimeMillis() % loopTime)) / (loopTime / (dotsInLoop + 1));
			for (int i = 0; i < dots; i++) {
				loading += ".";
			}
			g2d.setColor(Main.FONT_COLOR);
			g2d.drawString(loading, border, Main.HEIGHT - border);

		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		room.peek().keyPressed(e);
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
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
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
	public static void pushRoom(Room room, String transition) {
		if (transition == null) {
			HoboEscape.room.push(room);
		} else {
			pushRoom(room, null);
			HoboEscape.room.push(Transition.getTransition(transition, HoboEscape.room.elementAt(HoboEscape.room.size() - 2), room));
		}
	}

	/**
	 * pass null for transition if you want it to just cut to the next room.
	 * 
	 * @param room
	 * @param transition
	 */
	
	public static void setRoom(Room room, String transition) {
		if(transition == null) {
			HoboEscape.room.set(HoboEscape.room.size() - 1, room);
		}else {
			HoboEscape.room.set(HoboEscape.room.size() - 1, (Transition.getTransition(transition, HoboEscape.room.peek(), room)));
		}
	}

	public static void popRoom(String string) {
		if(string == null)
			room.pop();
		else {
			setRoom(HoboEscape.room.elementAt(HoboEscape.room.size() - 2), string);
			//TODO yeah the transition thing...
		}
	}
}
