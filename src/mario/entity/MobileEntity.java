package mario.entity;

import javafx.geometry.Point2D;
import mario.common.Orientation;

public class MobileEntity extends Entity {
	private Point2D velocity;
	private boolean isOnGround;
	private Orientation orientation = Orientation.Left;
	
	public void setVelocity(Point2D velocity) {
		this.velocity = velocity;
	}
	
	public Point2D getVelocity() {
		return this.velocity;
	}
	
	public boolean isOnGround() {
		return this.isOnGround;
	}
	
	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}
	
	public Orientation getOrientation() {
		return this.orientation;
	}
}
