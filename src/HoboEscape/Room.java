package HoboEscape;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * this class is the parent class for dealing with rooms. it has an offset and that is taken account of in the button class as well.
 * all because animation is awesome.
 * @author mgosselin
 *
 */
public abstract class Room {
	/**
	 * this, im sure, is bad practice. but oh well. im not doing the static
	 * constructor crap and im sure as hell not making an object of room to
	 * instantiate the multiple objects... of room... for obvious...
	 * recursive.... reasons...
	 */
	public static final Room MENU = new Menu();
	public static final Room GAME_ROOM = new GameRoom();
	public static final Room LEVELS = new LevelRoom();
	public static final Room SPLASH_SCREEN = new SplashScreen();
	public static final Room PAUSE_MENU = new PauseMenu();
	public static final Room OPTIONS_ROOM = new OptionsRoom();
	
	private int x = 0, y = 0;
	
	public final void paint(Graphics2D g) {
		Image image = new BufferedImage(Main.WIDTH, Main.HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D)image.getGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		render(g2d);
		g.drawImage(image, x, y, null);
		if(Main.debug > 0) {
			g.setColor(Color.GREEN);
			g.drawRect(x, y, Main.WIDTH - 1, Main.HEIGHT - 1);
		}
	}
	
	
	/**
	 * this is protected because its not called directly. you need to call the
	 * paint method. it will factor in things like animation position and such
	 * so all you have to worry about in the implementation of render is
	 * the stuff thats actually on the screen. we take care of the rest!
	 * @param g
	 */
	protected abstract void render(Graphics2D g);
	
	public abstract void tick();
	public abstract void keyPressed(KeyEvent e);
	
	/**
	 * literally just used to calculate input offsets.
	 * from what i saw though it looks implemented wrong so...
	 * @return
	 */
	//TODO ^^^^^^^^^^^^^^^^
	public final int getX() {
		return x;
	}
	
	public final int getY() {
		return y;
	}
	
	/**
	 * basically, be careful with this... it literally changes
	 * where the frame is drawn. if you screw with this, just
	 * leave it how you found it. or at the origin. usually you
	 * should always find it at the origin so the two are
	 * kindof synonymous... 
	 * @param x
	 * @param y
	 */
	public final void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}
}