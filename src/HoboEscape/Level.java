package HoboEscape;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Level {
	private String name;
	private boolean exists, locked;
	
	//base level root
	private String varPath;
	private String fullPath;

	private int rightBound;
	
	public Level(int id) {
		varPath = "Levels\\" + id;
		fullPath = Main.BASE_DIR + "Levels\\" + id;
		exists = new File(fullPath + "\\level.exists").exists();
		if (exists) {
			locked = Boolean.parseBoolean(new Variable(varPath, "locked", "false", false).getValue());
			name = new Variable(varPath, "name", "--name--", false).getValue();
		} else {
			locked = true;
			name = "nopenopenopenopenope";
		}
	}

	public Set<WorldComponent> parseComponents(String room) {
		Set<WorldComponent> components = new HashSet<WorldComponent>();
		File file = new File(fullPath + "\\" + room + ".lvl");
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int x = 0, y = 0;
		String type = "";
		String data = "";
		while (scanner.hasNextLine()) {
			String str = scanner.nextLine();
			str = str.replace(" ", "").replace(";", "").toLowerCase();
			if (str.startsWith("#")) {
				// ignore this line because derp
			} else if (str.startsWith("x=")) {
				x = Integer.parseInt(str.substring(2));
			} else if (str.startsWith("y=")) {
				y = Integer.parseInt(str.substring(2));
			} else if (str.startsWith("data=")) {
				data = str.substring(5);
			} else if (str.startsWith("type=")) {
				type = str.substring(5);
			} else if (str.equals("apply")) {
				System.out.print("Type: " + type + "\n" + "Data: " + data + "\n" + "X:    " + x + "\n" + "Y:    " + y + "\n\n");
				if (type.equals("tile")) {
					components.add(new WorldComponent(x * Main.TILE_RES, y * Main.TILE_RES, Images.getTexture(data), true));
				} else if (type.equals("tile-scenery")) {
					components.add(new WorldComponent(x * Main.TILE_RES, y * Main.TILE_RES, Images.getTexture(data), false));
				} else if (type.equals("width")) {
					// width = Integer.parseInt(data);
				} else if (type.equals("entity-block")) {
					if (data.equals("tree")) {
						// pplz, we made a tree.
						// a walking tree
						components.add(new Tree(x * Main.TILE_RES, y * Main.TILE_RES));
					} else if(data.equals("player")) {
						components.add(new Player(x*Main.TILE_RES, y*Main.TILE_RES));
					}
				} else if (type.equals("meta")) {
					if(data.equals("dimensions")) {
						rightBound = x * Main.TILE_RES;
					}
				}
			}

		}
		return components;
	}

	public String getName() {
		return name;
	}

	public boolean unlocked() {
		return !locked;
	}

	public boolean exists() {
		return exists;
	}
	
	public int getRightBound() {
		return rightBound;
	}
}