package HoboEscape;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class GameRoom extends Room {

	public static Set<WorldComponent> components;
	public static double camX = 0;
	private static Level currentLevel;
	public static GameRoom staticObject;
	
	public GameRoom() {
		components = new HashSet<WorldComponent>();
		staticObject = this;
	}

	@Override
	public void render(Graphics2D g) {
		
		
		
		camX = Player.getX() - Main.WIDTH / 2 + 16;
		
		
		//left bound...
		camX = camX < 0 ? 0 : camX;
		//how to right bound?
		
		
		for (WorldComponent worldComponent : components) {
			worldComponent.paint(g, (int)camX);
		}
		
		
	}

	@Override
	public void tick() {
		// check if goals met
		// ... few months later... at some point...
		
		for (WorldComponent worldComponent : components) {
			worldComponent.tick();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			HoboEscape.setRoom(new PauseMenu(staticObject), "panUp");
		}
	}

	public static void setLevel(Level level) {

		currentLevel = level;

		setRoom("boot");

	}

	public static void setRoom(String room) {
		components = currentLevel.parseComponents(room);
	}

	public static boolean collidableAt(int x, int y, WorldComponent ignore) {
		boolean collidable = false;
		Iterator<WorldComponent> iterator = components.iterator();
		while (iterator.hasNext() && !(collidable)) {
			WorldComponent component = iterator.next();
			if (ignore == component)
				continue;
			collidable |= component.collidableAt(x, y);
		}
		return collidable;
	}
}
