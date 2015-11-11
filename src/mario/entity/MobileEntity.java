package mario.entity;

import javafx.geometry.Point2D;
import mario.common.Orientation;

public class MobileEntity extends Entity {
	private Point2D velocity = new Point2D(0, 0);
	private Point2D maxVelocity = new Point2D(3, 3);
	private Point2D maxAcceleration = new Point2D(3, 3);
	
	private boolean isOnGround;
	
	private Orientation orientation = Orientation.Left;
	
	public void setVelocity(Point2D velocity) {
		this.velocity = velocity;
		
		if (this.velocity.getX() < 0) {
			this.orientation = Orientation.Left;
		} else {
			this.orientation = Orientation.Right;
		}
	}
	
	public Point2D getVelocity() {
		return this.velocity;
	}
	
	public Point2D getMaxVelocity() {
		return this.maxVelocity;
	}
	
	public void setMaxVelocity(Point2D maxVelocity) {
		this.maxVelocity = maxVelocity;
	}
	
	public Point2D getMaxAcceleration() {
		return this.maxAcceleration;
	}
	
	public void setMaxAcceleration(Point2D maxAcceleration) {
		this.maxAcceleration = maxAcceleration;
	}
	
	void setOnGround(boolean isOnGround) {
		this.isOnGround = isOnGround;
	}
	
	public boolean isOnGround() {
		return this.isOnGround;
	}
	
	public Orientation getOrientation() {
		return this.orientation;
	}
}
