package HoboEscape;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class Transition extends Room {

	private Room roomFrom, roomTo;
	private double modifier = 3.5d;
	private double transitionTime = 0;
	public boolean finished = false;
	private final Direction direction;

	protected Transition(Room roomFrom, Room roomTo, Direction direction) {
		this.roomFrom = roomFrom;
		this.roomTo = roomTo;
		roomTo.setPos(Main.WIDTH, 0);
		this.direction = direction;
	}

	@Override
	public void render(Graphics2D g) {
		roomFrom.paint(g);
		roomTo.paint(g);
	}

	@Override
	public void tick() {
		//System.out.println(transitionTime);
		
		// where to place things based up one time...
		if (direction == Direction.RIGHT) {
			roomFrom.setPos((int) (0 - (transitionTime * Main.WIDTH)), 0);
			roomTo.setPos((int) (Main.WIDTH - (transitionTime * Main.WIDTH)), 0);
		} else if (direction == Direction.LEFT) {
			roomFrom.setPos((int)(transitionTime * Main.WIDTH), 0);
			roomTo.setPos((int)((transitionTime) * Main.WIDTH) - Main.WIDTH, 0);
		}else if (direction == Direction.UP) {
			roomFrom.setPos(0, (int)(transitionTime * Main.HEIGHT));
			roomTo.setPos(0, (int)((transitionTime) * Main.HEIGHT) - Main.HEIGHT);
		}else if (direction == Direction.DOWN) {
			roomFrom.setPos(0, -(int)(transitionTime * Main.HEIGHT));
			roomTo.setPos(0, Main.HEIGHT - (int)((transitionTime) * Main.HEIGHT));
		}
		
		roomFrom.tick();
		roomTo.tick();
		transitionTime += (1 - transitionTime) / modifier;
		if (transitionTime > 0.999d) {
			finished = true;
			finish();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void finish() {
		roomFrom.setPos(0, 0);
		roomTo.setPos(0, 0);
		HoboEscape.popRoom(null);
	}

	public static Room getTransition(String transition, Room from, Room to) {
		if(transition.equalsIgnoreCase("panLeft")) {
			return new Transition(from, to, Direction.LEFT);
		}else if(transition.equalsIgnoreCase("panRight")) {
			return new Transition(from, to, Direction.RIGHT);
		}else if(transition.equalsIgnoreCase("panUp")) {
			return new Transition(from, to, Direction.UP);
		}else if(transition.equalsIgnoreCase("panDown")) {
			return new Transition(from, to, Direction.DOWN);
		}
		return to;
	}
	
	public String toString() {
		return "[" + roomFrom + " -> " + roomTo + "]";
	}

}
