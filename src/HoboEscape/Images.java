package HoboEscape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Images {
	public static BufferedImage NO_TEXTURE;
	public static BufferedImage logo;
	public static BufferedImage title;
	public static BufferedImage splashBackground;
	public static BufferedImage titleScreenBackground;
	public static BufferedImage paused;
	public static BufferedImage fog;

	// tilesets
	public static BufferedImage grassTileset;
	
	// blocks of sorts
	public static BufferedImage grass, grassLeft, grassRight, dirt, log, tree, grassSingle, bigTree;
	
	public static BufferedImage playerSheet, playerMask;
	public static BufferedImage overlayBackground;

	public Images() {
		try {
			
			long startTime = System.currentTimeMillis();
			
			
			try {
				logo = ImageIO.read(new File(Main.BASE_DIR + "Logo.png"));
				title = ImageIO.read(new File(Main.BASE_DIR + "Title.png"));
				paused = ImageIO.read(new File(Main.BASE_DIR + "Paused.png"));
				title = ImageHelper.getScaledImage(title, 0.6d);
				logo = ImageHelper.getScaledImage(logo, 0.6d);
				paused = ImageHelper.getScaledImage(paused, 0.7d);
			} catch (Exception e) {
				logo = getFlat(200, 50, 50, 50, 50, 255);
				title = getFlat(200, 50, 50, 50, 50, 255);
				paused = getFlat(200, 50, 50, 50, 50, 255);
			}
			try {
				grassTileset = ImageIO.read(new File(Main.BASE_DIR + "Tilesets\\BaseTileset.png"));
			} catch (Exception e) {
				grassTileset = getFlat(5 * 64, 7 * 64, 20, 150, 10, 255);
			}

			try {
				playerSheet = ImageIO.read(new File(Main.BASE_DIR + "Tilesets\\PlayerSheet.png"));
			} catch (Exception e) {
				playerSheet = getFlat(32, 48, 50, 50, 50, 255);
			}
			// cloudSheet = ImageIO.read(new File(HoboEscape.BASE_DIR +
			// "Tilesets\\CloudSheet.png"));

			grass = createTile(grassTileset, 64, 1, 1, 1, 1);
			grassLeft = createTile(grassTileset, 64, 0, 1, 1, 1);
			grassRight = createTile(grassTileset, 64, 2, 1, 1, 1);
			dirt = createTile(grassTileset, 64, 1, 2, 1, 1);
			log = createTile(grassTileset, 64, 5, 0, 2, 1);
			tree = createTile(grassTileset, 64, 1, 0, 1, 1);
			grassSingle = createTile(grassTileset, 64, 7, 1, 1, 1);
			bigTree = createTile(grassTileset, 64, 0, 4, 3, 3);
			
			System.out.println("Creating textures...");
			splashBackground = ImageHelper.creatImageWithStripes(Main.WIDTH, Main.HEIGHT, Main.BACKGROUND_COLOR);
			titleScreenBackground = ImageHelper.creatImageWithStripes(Main.WIDTH, Main.HEIGHT, Main.BACKGROUND_COLOR);

			// playerMask = createTile(playerSheet, 32, 48, 0, 0, 1, 1);
			playerMask = tree;
			// clouds[0] = createTile(64, 64, );

			BufferedImage temp = ImageHelper.creatImageWithStripes(Main.WIDTH, Main.HEIGHT, new Color(50, 50, 50));
			overlayBackground = new BufferedImage(Main.WIDTH, Main.HEIGHT, BufferedImage.TRANSLUCENT);
			overlayBackground.getGraphics().drawImage(temp, 0, 0, Main.WIDTH, Main.HEIGHT, 0, 0, Main.WIDTH, Main.HEIGHT, null);

			NO_TEXTURE = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
			
			System.out.println("finished creating textures! (" + (System.currentTimeMillis() - startTime) + "ms)");

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static BufferedImage getTexture(String texture) {
		switch(texture) {
		case "grass":
			return grass;
		case "grassLeft":
			return grassLeft;
		case "grassRight":
			return grassRight;
		case "dirt":
			return dirt;
		case "log":
			return log;
		case "tree":
			return tree;
		case "bigTree":
			return bigTree;
		case "player_mask":
			return playerMask;
		case "grassSingle":
			return grassSingle;
		default:
			return null;
		}
	}

	private static BufferedImage createTile(BufferedImage tileset, int tileRes, int x, int y, int width, int height) {
		BufferedImage image = new BufferedImage(tileRes * width, tileRes * height, BufferedImage.TRANSLUCENT);
		Graphics g = image.getGraphics();
		g.drawImage(tileset, 0, 0, tileRes * width, tileRes * height, (x * tileRes), (y * tileRes), (x * tileRes) + (width * tileRes), (y * tileRes) + (height * tileRes), null);
		return getScaledImage(image, Main.TILE_RES * width, Main.TILE_RES * height);
	}

	private static BufferedImage createTile(BufferedImage tileset, int tileResWidth, int tileResHeight, int x, int y, int width, int height) {
		BufferedImage image = new BufferedImage(tileResWidth * width, tileResHeight * height, BufferedImage.TRANSLUCENT);
		Graphics g = image.getGraphics();
		g.drawImage(tileset, 0, 0, tileResWidth * width, tileResHeight * height, (x * tileResWidth), (y * tileResHeight), (x * tileResWidth) + (width * tileResWidth), (y * tileResHeight) + (height * tileResHeight), null);
		return image;
	}

	public static BufferedImage getScaledImage(BufferedImage image, int width, int height) {
		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();

		double scaleX = (double) width / imageWidth;
		double scaleY = (double) height / imageHeight;
		AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
		AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);

		return bilinearScaleOp.filter(image, new BufferedImage(width, height, image.getType()));
	}

	public static BufferedImage getFlat(int width, int height, int r, int g, int b, int a) {
		Color c = new Color(r, g, b, a);
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics graphics = image.getGraphics();
		graphics.setColor(c);
		graphics.fillRect(0, 0, width, height);
		return image;
	}
}