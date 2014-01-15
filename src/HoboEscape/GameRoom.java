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
	private static double camX = 0;
	private static Level currentLevel;
	public static GameRoom staticObject;
	private static Cycler cycler1, cycler2;
	
	public GameRoom() {
		components = new HashSet<WorldComponent>();
		staticObject = this;
		cycler1 = new Cycler();
		cycler2 = new Cycler(50, 3);
	}

	@Override
	public void render(Graphics2D g) {
		camX = Player.getX() - (Main.WIDTH / 2) + (Player.dx * 10);
		camX = camX < 0 ? 0 : camX;
		for (WorldComponent worldComponent : components) {
			worldComponent.paint(g, (int)camX);
		}
		
		//render fog

		// cycler1.render(g);
		// cycler2.render(g);
		
	}

	@Override
	public void tick() {
		// check if goals met

		for (WorldComponent worldComponent : components) {
			worldComponent.tick();
		}

		cycler1.tick();
		cycler2.tick();
		
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
