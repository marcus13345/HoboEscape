package HoboEscape;

import java.awt.Graphics2D;
import java.util.Stack;

public class ButtonSet {
	private Stack<Button> buttons = new Stack<Button>();

	private ButtonListener listener;

	public ButtonSet() {

	}

	public void setListener(ButtonListener buttonListener) {
		listener = buttonListener;
	}

	public void pollAll() {
		for (int i = 0; i < buttons.size(); i++) {
			buttons.elementAt(i).poll();
			while (buttons.elementAt(i).hasNextClick()) {
				if (listener != null)
					listener.click(buttons.elementAt(i).getNameID(), buttons.elementAt(i).getID());
			}
		}
	}

	public void renderAll(Graphics2D g) {
		g.setFont(Main.BASE_FONT);
		for (int i = 0; i < buttons.size(); i++)
			buttons.elementAt(i).render(g);
	}

	public void addButton(Button b) {
		buttons.push(b);
	}

	public void setButtonPos(int i, int x, int y) {
		buttons.elementAt(i).updatePosition(x, y);
	}

	public void resetAll() {
		for (int i = 0; i < buttons.size(); i++)
			buttons.elementAt(i).resetButtonState();
	}

	public void reset(int i) {
		buttons.elementAt(i).resetButtonState();
	}
	
	public void changeButtonName(int id, String name) {
		buttons.elementAt(id).changeName(name);
	}
}
