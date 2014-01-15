package HoboEscape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Images {
	public static BufferedImage logo;
	public static BufferedImage title;
	public static BufferedImage splashBackground;
	public static BufferedImage titleScreenBackground;
	public static BufferedImage paused;
	public static BufferedImage fog;

	// tilesets
	private static BufferedImage grassTileset;
	private static BufferedImage grass, grassLeft, grassRight, dirt, log, tree, grassSingle, bigTree;
	private static BufferedImage playerSheet, playerMask;
	public static BufferedImage overlayBackground;

	public Images() {
		try {
			splashBackground = ImageCreator.creatImageWithStripes(Main.WIDTH, Main.HEIGHT, Main.FOREGROUND_COLOR);
			titleScreenBackground = ImageCreator.creatImageWithStripes(Main.WIDTH, Main.HEIGHT, Main.BACKGROUND_COLOR);
			try {
				logo = ImageIO.read(new File(Main.BASE_DIR + "Logo.png"));
				title = ImageIO.read(new File(Main.BASE_DIR + "Title.png"));
				paused = ImageIO.read(new File(Main.BASE_DIR + "Paused.png"));
				title = ImageCreator.getScaledImage(title, 0.6d);
				logo = ImageCreator.getScaledImage(logo, 0.6d);
				paused = ImageCreator.getScaledImage(paused, 0.7d);
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
			

			playerMask = createTile(playerSheet, 32, 48, 0, 0, 1, 1);
			// clouds[0] = createTile(64, 64, );

			BufferedImage temp = ImageCreator.creatImageWithStripes(Main.WIDTH, Main.HEIGHT, new Color(50, 50, 50));
			overlayBackground = new BufferedImage(Main.WIDTH, Main.HEIGHT, BufferedImage.TRANSLUCENT);
			overlayBackground.getGraphics().drawImage(temp, 0, 0, Main.WIDTH, Main.HEIGHT, 0, 0, Main.WIDTH, Main.HEIGHT, null);

			System.out.println("here");

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static BufferedImage getTexture(String texture) {
		if (texture.equals("grass")) {
			return grass;
		} else if (texture.equals("grass_left")) {
			return grassLeft;
		} else if (texture.equals("grass_right")) {
			return grassRight;
		} else if (texture.equals("dirt")) {
			return dirt;
		} else if (texture.equals("log")) {
			return log;
		} else if (texture.equals("tree")) {
			return tree;
		} else if (texture.equals("big_tree")) {
			return bigTree;
		} else if (texture.equals("player_mask")) {
			return playerMask;
		} else if (texture.equals("grass_single")) {
			return grassSingle;
		}
		return null;
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