package HoboEscape;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 * this is the pause menu. again, exactly what it sounds like. it is the room that does everything that one may need in a pause menu.
 * except all it does is resume the game. another one of the many "features" in this game.
 * @author mgosselin
 *
 */
public class PauseMenu extends Room implements ButtonListener{

	private ButtonSet buttons;
	
	public PauseMenu() {
		//super(under);
		buttons = new ButtonSet();
		buttons.addButton(new Button(this, 0, Main.BUTTON_COLOR, 100, 100, 200, 50, "Resume", 20, 10));
		buttons.addButton(new Button(this, 0, Main.BUTTON_COLOR, 100, 170, 200, 50, "Options", 20, 10));
		buttons.addButton(new Button(this, 0, Main.BUTTON_COLOR, 100, 240, 200, 50, "Quit", 20, 10));
		buttons.addButton(new Button(this, 0, Main.BUTTON_COLOR, 100, 500, 200, 50, "Quit", 20, 10));
		buttons.setListener(this);
	}
	/*
	@Override
	protected void renderOverlay(Graphics2D g) {
		buttons.renderAll(g);
		
	}*/
	
	public void tick() {
		buttons.pollAll();
	}

	@Override
	public void click(String name, int id) {
		if(id == 0) {
			//resume button...
			finish();
		}else if(id == 1) {
			HoboEscape.pushRoom(Room.OPTIONS_ROOM, "PanLeft");
		}
	}

	@Override
	protected void render(Graphics2D g) {
		// TODO Auto-generated method stub
		buttons.renderAll(g);
		g.drawImage(Images.paused, ((Main.WIDTH / 2) - (Images.paused.getWidth() / 2)), 20, null);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			finish();
		}
	}
	
	private void finish() {
		HoboEscape.popRoom("panDown");
	}
	
	public String toString() {
		return "Pause Menu";
	}
}
