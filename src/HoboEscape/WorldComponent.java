package HoboEscape;

import java.awt.Graphics2D;
import java.awt.Image;

public class WorldComponent {
	protected double x, y, width, height;
	protected Image texture;
	private boolean alive;
	private final boolean collidable;
	
	public static final Player player = new Player(0, 0);
	
	public WorldComponent(int x, int y, Image texture, boolean collidable) {
		this.x = x;
		this.y = y;
		this.width = texture.getWidth(null);
		this.height = texture.getHeight(null);
		this.texture = texture;
		alive = true;
		this.collidable = collidable;
	}

	/**
	 * if you aren't using this base class with the nice proper constructor, you
	 * should always override this.
	 * 
	 * basically, don't listen to that guy ^^
	 * wait, why?
	 * 
	 * oh i get it, it just calls the thing, you need to implement it youself. if you feel like it.
	 * 
	 * @param g
	 * @param camX
	 */
	public final void paint(Graphics2D g, int camX) {
		render(g, camX);
	}

	protected void render(Graphics2D g, int camX) {
		if(texture == null)
			g.drawImage(texture, (int)x - camX, (int)y, null);
		else
			g.drawImage(Images.NO_TEXTURE, 0, 0, null);
		
		
		
	}

	public void tick() {

	}

	public boolean collidableAt(int x2, int y2) {
		// System.out.println("" + x2 + " > " + x + " && " + x2 + " < " + (x +
		// width) + " && " + y2 + " > " + y + " && " + y2 + " < " + (y +
		// height));
		return collidable && x2 >= x && x2 <= x + width && y2 > y && y2 <= y + height;
	}
}
