package HoboEscape;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 * this class is loose with the definition of static so please only make one instance. 
 * i don't ever want more than one player controlled character so whatever.
 * 
 * @author marcus
 *
 */
public class Player extends WorldComponent {
	private static final double GRAVITY = 0.2d;
	private static final double JUMP_POWER = 5d;
	private static final double MAX_SPEED = 2d;
	private static final double MAX_RUNNING_SPEED = 5d;
	private static final double ACC = 0.4d;

	public static double dy = 0, dx = 0;
	private static boolean onGround = false;
	private static boolean topLeft, topRight, bottomLeft, bottomRight;
	private static byte flags;
	private static Player staticPlayer;

	public Player(int x, int y) {
		super(x, y, Images.getTexture("player_mask"), true);
		staticPlayer = this;
	}
	
	public void tick() {
		// input
		if (onGround) {
			if (HoboEscape.keyChars['a'] && !HoboEscape.keyChars['d']) {
				// pressing left while on the ground, LOVELY
				dx -= ACC;
			} else if (HoboEscape.keyChars['d'] && !HoboEscape.keyChars['a']) {
				// pressing right while on the ground, LOVELY
				dx += ACC;
			} else {
				// slow dowwwwwn
				if (dx >= ACC)
					dx -= ACC;
				else if (dx <= -ACC)
					dx += ACC;
				else
					dx = 0;
			}
			if(HoboEscape.keyChars[' ']) {
				dy = -JUMP_POWER;
			}
		}

		// gravity
		dy += GRAVITY;

		//Keyboard asdf;
		
		//cap dx and dy
		if(HoboEscape.keyCodes[KeyEvent.VK_SHIFT])
			dx = dx < -MAX_RUNNING_SPEED ? -MAX_RUNNING_SPEED : dx > MAX_RUNNING_SPEED ? MAX_RUNNING_SPEED : dx;
		else
			dx = dx < -MAX_SPEED ? -MAX_SPEED : dx > MAX_SPEED ? MAX_SPEED : dx;
		
		
		// actually affect x and y now
		x += dx * 2;
		y += dy * 2;

		// proper collisions...
		if (HoboEscape.keyChars['r']) {
			y = 100;
			x = 500;
			dy = 0;
		}
		calculateTestPoints();

		// after this point, do NOT use the onground variable unless you are
		// setting it accordingly
		// because you know... its wrong. and stuff.
		onGround = false;
		if (flags == 0b00000011) {

			while ((flags & 0b00000011) != 0b00000000) {
				y--;
				dy = 0;
				calculateTestPoints();
			}
			onGround = true;
			// simple flag case here, boys....
		} else if (flags == 0b00000010) {
			// and now for some witwut, wutwat.witwutwatwutwit. zach lee.
			final double yBefore = y, xBefore = x, newY, newX;
			int dy = 0;
			int dx = 0;

			calculateTestPoints();
			while ((flags & 0b00000010) != 0b00000000) {
				y--;
				dy++;
				calculateTestPoints();
			}
			newY = y;
			y = yBefore;

			calculateTestPoints();
			while ((flags & 0b00000010) != 0b00000000) {
				x++;
				dx++;
				calculateTestPoints();
			}
			newX = x;
			x = xBefore;

			calculateTestPoints();
			/*
			 * if(dx == dy) { // how the bloddy hell did you pull that one off.
			 * dx = 0; x = newX; //just fall because if you didn't then it would
			 * be like wuuuuuut } else if (dy > dx) { //if it takes more to move
			 * up than to move to the side... }else{ //stay on the platform... }
			 */
			// BETTER WAY TO DO DYS
			// SIMPLAR
			if (dy < dx & dy >= 0) {
				this.dy = 0;
				y = newY;
				onGround = true;
				// change dat y, get on top ;)
			} else {
				this.dx = 0;
				x = newX;
			}
		} else if (flags == 0b00000001) {
			// same as before. but with a different x test because derp.
			final double yBefore = y, xBefore = x, newY, newX;
			int dy = 0, dx = 0;

			//TODO make this entire place better. its like super inefficient...
			
			calculateTestPoints();
			while ((flags & 0b00000001) != 0b00000000) {
				y--;
				dy++;
				calculateTestPoints();
			}
			newY = y;
			y = yBefore;

			calculateTestPoints();
			while ((flags & 0b00000001) != 0b00000000) {
				x--;
				dx++;
				calculateTestPoints();
			}
			newX = x;
			x = xBefore;

			calculateTestPoints();
			/*
			 * if(dx == dy) { // how the bloddy hell did you pull that one off.
			 * dx = 0; x = newX; //just fall because if you didn't then it would
			 * be like wuuuuuut } else if (dy > dx) { //if it takes more to move
			 * up than to move to the side... }else{ //stay on the platform... }
			 */
			// BETTER WAY TO DO DYS
			// SIMPLAR
			if (dy < dx & dy >= 0) {
				this.dy = 0;
				y = newY;
				onGround = true;
			} else {
				this.dx = 0;
				x = newX;
			}
		}

	}

	private void calculateTestPoints() {
		final int width = texture.getWidth(null);
		final int height = texture.getHeight(null);
		topLeft = GameRoom.collidableAt((int) x, (int) y, this);
		topRight = GameRoom.collidableAt((int) x + width, (int) y, this);
		bottomLeft = GameRoom.collidableAt((int) x, (int) y + height, this);
		bottomRight = GameRoom.collidableAt((int) x + width, (int) y + height, this);

		// stuff that i whipped up in 40 minutes because
		// i never learned how to use bytes
		// properly when not in an int. yeh.

		flags = 0;
		flags += ((topLeft ? 0b00001000 : 0b00000000) | (topRight ? 0b00000100 : 0b00000000) | (bottomLeft ? 0b00000010 : 0b00000000) | (bottomRight ? 0b00000001 : 0b00000000));

	}
	
	/**
	 * solely for the purpose of calculating the proper x position of the camera
	 * in the world.
	 * 
	 */
	public static int getX() {
		return (int)staticPlayer.x;
	}
}
