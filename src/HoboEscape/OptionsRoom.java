package HoboEscape;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;


public class OptionsRoom extends Room implements ButtonListener {
	
	private ButtonSet buttons;
	
	public OptionsRoom() {
		buttons = new ButtonSet();
		buttons.addButton(new Button(this, 0, Main.BUTTON_COLOR, 0, 0, 200, 50, "Sound: " + (Globals.sound ? "On" : "Off"), 0, 0));
		buttons.setListener(this);
		
	}

	@Override
	protected void render(Graphics2D g) {
		
	}

	@Override
	public void tick() {
		buttons.pollAll();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void click(String name, int id) {
		// TODO Auto-generated method stub
		
	}

}
