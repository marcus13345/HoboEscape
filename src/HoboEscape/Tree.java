package HoboEscape;

public class Tree extends WorldComponent {
	private static final int LEFT = 0, RIGHT = 1, TURN_LEFT = 2, TURN_RIGHT = 3;
	private int animationTime;
	private int animation;
	private boolean foot = false, wall = false;

	public Tree(int x, int y) {
		super(x, y, Images.getTexture("tree"), false);
		setAnimation(RIGHT);
	}

	public void tick() {

		if (dy == 0) {
			boolean newFoot = foot;
			boolean newWall = wall;

			if (animation == LEFT) {
				
				if(!GameRoom.collidableAt((int)x - 1, (int)y + (int)height + 1, this)) {
					setAnimation(TURN_RIGHT);
				}else if(GameRoom.collidableAt((int)x - 1, (int)y + (int)(height / 2), this)) {
					setAnimation(TURN_RIGHT);
				}
				
			} else if (animation == RIGHT) {
				if(!GameRoom.collidableAt((int)x + (int)width, (int)y + (int)height + 1, this)) {
					setAnimation(TURN_LEFT);
				}else if(GameRoom.collidableAt((int)x + (int)width, (int)y + (int)(height / 2), this)) {
					setAnimation(TURN_LEFT);
				}
			}

			foot = newFoot;
			wall = newWall;
		}
		
		dy += 0.25d;
		y += dy;
		while (GameRoom.collidableAt((int)x + (int)(width / 2), (int)y + (int)height, this)) {

			y--;
			dy = 0;
			y = (int) y;
		}
		
		animationTime++;
		switch (animation) {
		case RIGHT:
			x++;
			break;
		case LEFT:
			x--;
			break;
		case TURN_RIGHT:
			if (animationTime > 100) {
				setAnimation(RIGHT);
			}
			break;
		case TURN_LEFT:
			if (animationTime > 100) {
				setAnimation(LEFT);
				animationTime = 0;
			}
			break;
		}

	}

	private double dy = 0;
	
	private void setAnimation(int i) {
		animationTime = 0;
		animation = i;
	}

}
