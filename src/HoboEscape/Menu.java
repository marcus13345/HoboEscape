package HoboEscape;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class Menu extends Room implements ButtonListener{
	private static double titleX, desiredTitleX;
	private static final int TITLE_OFFSET = 4000;
	private static int buttonTime, buttonTimeOffset;
	private static ButtonSet buttonSet;
	private static Button[] buttons;
	private static final int BUTTON_WIDTH = 200;
	
	public Menu() {
		
		buttonTimeOffset = 20;
		buttonTime = 0 - buttonTimeOffset;
		//okay... ew
		titleX = 0;
		desiredTitleX = (Main.WIDTH / 2) - (Images.title.getWidth(null) / 2) + TITLE_OFFSET;
		buttons = new Button[4];
		buttons[0] = new Button(this, 0, Main.BUTTON_COLOR, Main.WIDTH, Main.HEIGHT, BUTTON_WIDTH, 40, "Play", 10, 10);
		buttons[1] = new Button(this, 1, Main.BUTTON_COLOR, Main.WIDTH, Main.HEIGHT, BUTTON_WIDTH, 40, "Options", 10, 10);
		buttons[2] = new Button(this, 2, Main.BUTTON_COLOR, Main.WIDTH, Main.HEIGHT, BUTTON_WIDTH, 40, "Credits", 10, 10);
		buttons[3] = new Button(this, 3, Main.BUTTON_COLOR, Main.WIDTH, Main.HEIGHT, BUTTON_WIDTH, 40, "Quit", 10, 10);
		
		buttonSet = new ButtonSet();

		for(int i = 0; i < buttons.length; i ++)
			buttonSet.addButton(buttons[i]);
		
		buttonSet.setListener(this);
		
	}
	
	@Override
	public void render(Graphics2D g) {
		g.drawImage(Images.title, (int)titleX - TITLE_OFFSET, 50, null);
		buttonSet.renderAll(g);
	}

	@Override
	public void tick() {
		buttonTime ++;
		titleX += (desiredTitleX - titleX) / 12d;
		buttonSet.pollAll();
		
		for (int i = 0; i < buttons.length; i++) { 
			
			double time = buttonTime / 10d;
			time -= (i * 1.1d);
			time = time < 0 ? 0 : time;
			time /= 1.5d;
			
			double offset = function(time);
			offset *= 100;
			
			
			buttons[i].updatePosition(200, 300 + (i * 60) + (int)offset);
		}
		
	}
	
	private double function(double x) {
		if(x <= 3) return Math.pow(x - 3, 2);
		else return 0;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@SuppressWarnings("unused")
	private Color vary(Color c, int k) {
		
		double r = c.getRed();
		double g = c.getGreen();
		double b = c.getBlue();
		
		for (int i = 0; i < k; i ++) {
			r *= 0.9d;
			g *= 0.9d;
			b *= 0.9d;

			r += Math.random() * 25;
			g += Math.random() * 25;
			b += Math.random() * 25;
			
		}
		
		return new Color((int)r, (int)g, (int)b);
		//return new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255));
	}

	@Override
	public void click(String name, int id) {
		if(id == 0) {
			HoboEscape.pushRoom(Room.LEVELS, "panRight");
		}
	}

	public static void stopAnimation() {
		titleX = desiredTitleX;
		buttonTime = 20;
	}

	public String toString() {
		return "Menu";
	}
}
