package HoboEscape;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class Cycler {
	private BufferedImage[] images;
	private double time = 0;
	private int MAX_TIME = 100;
	private int pointer = 0;
	public Cycler() {this(0, 0);}
	public Cycler(int offset, int offsetInFogArray) {
		images = new BufferedImage[3];
		for(int i = 0; i < images.length; i ++) {
			images[i] = Images.getTexture("fog" + (i + offsetInFogArray));
		}
		//images initialized...
		
		time = offset;
		
	}
	
	public void tick() {
		time += .25d;
		if(time >= MAX_TIME) {
			pointer++;
			time = 0;
			pointer %= images.length;
		}
	}
	
	public void render(Graphics2D g) {
		AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)(        (Math.sin(Math.PI * (time / (float)MAX_TIME))*1.5) > 1 ? 1 : (Math.sin(Math.PI * (time / (float)MAX_TIME))*1.5)         ));
		g.setComposite(composite);
		g.drawImage(images[pointer], 0, 0, null);
		composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
		g.setComposite(composite);
	}
}
