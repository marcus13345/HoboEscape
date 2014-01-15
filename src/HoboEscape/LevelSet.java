package HoboEscape;

import java.awt.Color;
import java.io.File;
import java.util.Stack;


public class LevelSet {

	private static Stack<Level> levels;
	private static final int 
			rows = 6, 
			topPadding = 100, 
			buttonMargin = 15, 
			buttonSize = (((Main.HEIGHT - topPadding*2 + buttonMargin) / rows) - buttonMargin), 
			leftPadding = 100, 
			yOffset = (int)(topPadding / 3.5);
	private static final double buttonWidthRatio = 5;
	
	public void initializeList() {
		levels = new Stack<Level>();
		for(int i = 0; i < 100; i ++) {
			Level level = new Level(i + 1);
			if(level.exists()) {
				levels.add(level);
			}
		}
	}

	public Button[] getButtonArray(Room parent) {
		Button[] buttons;
		buttons = new Button[levels.size()];
		
		for(int i = 0; i < buttons.length; i ++) {
			boolean active = levels.elementAt(i).unlocked();
			buttons[i] = new Button(parent, i, Main.BUTTON_COLOR, (int) (leftPadding + (Math.floor(i/(double)rows) * ((int)(buttonSize*buttonWidthRatio) + buttonMargin))), yOffset + (i%rows)*((Main.HEIGHT - topPadding*2 + buttonMargin)/(rows)) + topPadding, (int)(buttonSize*buttonWidthRatio), buttonSize, "" + levels.elementAt(i).getName(), 15, 15, active);
		}
		
		return buttons;
	}

	public Level getLevel(int id) {
		return levels.elementAt(id);
	}
}
