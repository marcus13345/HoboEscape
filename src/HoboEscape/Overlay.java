package HoboEscape;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;


public abstract class Overlay extends Room{
	private Room under;
	private static final float backgroundAlpha = 0.5f;
	private static AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, backgroundAlpha);
	private static AlphaComposite defaultComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);

	public Overlay(Room under) {
		this.under = under;
	}

	@Override
	protected void render(Graphics2D g) {
		under.paint(g);
		g.setColor(Color.BLACK);
		g.setComposite(alphaComposite);
		// g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		g.drawImage(Images.overlayBackground, 0, 0, null);
		g.setComposite(defaultComposite);
		renderOverlay(g);
	}
	
	protected abstract void renderOverlay(Graphics2D g);

	@Override
	public void tick() {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			finish();
		}
	}
	

	
	protected void finish() {
		HoboEscape.popRoom(null);
	}

}
