package HoboEscape;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class Levels extends Room implements ButtonListener {

	private static Button[] buttons;
	private static ButtonSet buttonSet;
	private static LevelSet levelSet;
	private static Levels levelsObject;
	public static Room staticObject;
	private static final int BACK_BUTTON_ID = -1;

	public Levels() {
		levelsObject = this;
		staticObject = this;
		rescanButtons();
	}

	@Override
	public void render(Graphics2D g) {
		buttonSet.renderAll(g);
		g.setFont(Main.BASE_FONT_TITLE);
		g.setColor(Color.BLACK);
		g.drawString("Level Select", 300, 80);
	}

	@Override
	public void tick() {
		buttonSet.pollAll();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			click("", -1);
		}
	}

	@Override
	public void click(String name, int id) {
		if(id == BACK_BUTTON_ID) {
			HoboEscape.setRoom(Menu.staticObject, "panLeft");
			return;
		}
		GameRoom.setLevel(levelSet.getLevel(id));
		HoboEscape.setRoom(GameRoom.staticObject, "panDown");
	}

	public static void rescanButtons() {
		levelSet = new LevelSet();

		levelSet.initializeList();

		buttons = levelSet.getButtonArray(levelsObject);

		buttonSet = new ButtonSet();

		for (int i = 0; i < buttons.length; i++)
			buttonSet.addButton(buttons[i]);
		buttonSet.addButton(new Button(levelsObject, BACK_BUTTON_ID, Color.GRAY, 50, 30, 100, 45, "Back", 20, 11));

		buttonSet.setListener(levelsObject);
		
		
		
	}

}
