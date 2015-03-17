package HoboEscape;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 * This is the actual level select room, in which you are given a selection of the available levels and can select one to play.
 * this does make copious calls to Level however, hence the fact the level doesn't pre parse any of the levels into components.
 * @author mgosselin
 *
 */
public class LevelRoom extends Room implements ButtonListener {

	private static Button[] buttons;
	private static ButtonSet buttonSet;
	private static LevelSet levelSet;
	private static LevelRoom levelsObject;
	private static final int BACK_BUTTON_ID = -1;

	public LevelRoom() {
		levelsObject = this;
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
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			click("", -1);
		}
	}

	@Override
	public void click(String name, int id) {
		if (id == BACK_BUTTON_ID) {
			HoboEscape.popRoom("panLeft");
			return;
		}
		GameRoom.setLevel(levelSet.getLevel(id));
		HoboEscape.pushRoom(Room.GAME_ROOM, "panDown");
	}

	public static void rescanButtons() {
		levelSet = new LevelSet();

		levelSet.initializeList();

		buttons = levelSet.getButtonArray(levelsObject);

		buttonSet = new ButtonSet();

		for (int i = 0; i < buttons.length; i++)
			buttonSet.addButton(buttons[i]);
		buttonSet.addButton(new Button(levelsObject, BACK_BUTTON_ID,
				Main.BUTTON_COLOR, 50, 30, 100, 45, "Back", 20, 11));

		buttonSet.setListener(levelsObject);

	}

	public String toString() {
		return "LevelSelect";
	}
}
