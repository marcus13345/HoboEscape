package HoboEscape;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;


public class SplashScreen extends Room {
	private static int maxTime = 120;
	private final static int TIME_OFFSET = 60;
	private static int time = 0 - TIME_OFFSET;
	private static double textTime = 0 - (Math.PI / 2);
	public static Room staticObject;
	
	public SplashScreen() {
		staticObject = this;
	}
	
	@Override
	public void render(Graphics2D g) {
		
		//alpha 1
		// g.drawImage(Images.splashBackground, 0, 0, null);
		//set alpha to time
		float alpha = (time)/(float)maxTime;
		alpha = alpha < 0 ? 0 : alpha > 1 ? 1 : alpha;
	    AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1 - alpha);
	    g.setComposite(composite);
	    //draw second
	    g.drawImage(Images.splashBackground, 0, 0, null);
	    //reset alpha
	    composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
	    g.setComposite(composite);
	    
	    //g.drawImage(Images.logo, (int)((Main.WIDTH / 2d) + Math.tan(textTime) * 40) - (int)(Main.WIDTH / 3d), Main.HEIGHT / 2, null);
	    
	    g.drawImage(Images.logo, (int)((Math.tan(textTime) * 135)) + 200, 200, null);
	    
	    //g.drawString("The Cleaning Lady:", (int)((Main.width / 2d) + tan(time) * 40) - (int)(Main.width / 3d), (int)(Main.height / 4d));
	    
	}

	@Override
	public void tick() {
		time ++;
		textTime += Math.PI / (TIME_OFFSET + maxTime);
		if(time > maxTime) {
			HoboEscape.setRoom(Menu.staticObject);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		HoboEscape.setRoom(Menu.staticObject);
		Menu.stopAnimation();
	}

}
