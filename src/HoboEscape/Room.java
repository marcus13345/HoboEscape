package HoboEscape;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;


public abstract class Room {
	private int x = 0, y = 0, width = Main.WIDTH, height = Main.HEIGHT;
	public final void paint(Graphics2D g) {
		Image image = new BufferedImage(Main.WIDTH, Main.HEIGHT, BufferedImage.TRANSLUCENT);
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
	protected abstract void render(Graphics2D g);
	public abstract void tick();
	public abstract void keyPressed(KeyEvent e);
	
	public final int getX() {
		return x;
	}
	
	public final int getY() {
		return y;
	}
	
	public final void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}
}