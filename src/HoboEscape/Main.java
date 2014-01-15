package HoboEscape;

import java.awt.Color;
import java.awt.Font;
import java.io.File;

import javax.imageio.ImageIO;


public class Main {
	
	public static final int ASPECT_WIDTH = 16;
	public static final int ASPECT_HEIGHT = 9;
	public static final int MULTIPLIER = 1, TILE_RES = 64;
	public static final int WIDTH = ASPECT_WIDTH * MULTIPLIER * TILE_RES;
	public static final int HEIGHT = ASPECT_HEIGHT * MULTIPLIER * TILE_RES;
	public static final Color BACKGROUND_COLOR = new Color(20, 100, 30);
	public static final Color FOREGROUND_COLOR = new Color(200, 40, 40);
	public static final Color FONT_COLOR = new Color(0, 0, 0);
	public static int debug = 0;
	public static Font BASE_FONT, BASE_FONT_TITLE;
	private static final double DIAGONAL = Math.sqrt(Math.pow(WIDTH, 2) + Math.pow(HEIGHT, 2)) / 1174.8838240438924;
	
	public static final String BASE_DIR = System.getenv("APPDATA") + "\\MAndWorks\\HoboEscape\\";
	
	public static void main(String[] args) {
		
		try {
			BASE_FONT = Font.createFont(Font.TRUETYPE_FONT, new File(Main.BASE_DIR + "Neucha.ttf")).deriveFont(Font.BOLD, 30);
			BASE_FONT_TITLE = BASE_FONT.deriveFont(Font.BOLD, 62);
			System.out.println(DIAGONAL);
		} catch(Exception e) {
			BASE_FONT = new Font("Tahoma", Font.PLAIN, 30);
			BASE_FONT_TITLE = new Font("Tahoma", Font.BOLD, 62);
		}
		new HoboEscape();
	}
}