package fr.utbm.tc.qlearningmario.mario.entity;

import fr.utbm.tc.qlearningmario.mario.common.Orientation;
import javafx.geometry.Point2D;

public class MobileEntity extends Entity {
	private Point2D velocity = Point2D.ZERO;
	private Point2D maxVelocity = new Point2D(3, 3);
	private Point2D maxAcceleration = new Point2D(3, 16);
	
	private boolean isOnGround = true;
	
	private Orientation orientation = Orientation.Right;
	
	public void setVelocity(Point2D velocity) {
		// FIXME: Put message in assertion when possible.
		assert (velocity != null) : "You must give a null velocity object";
		double velocityX = velocity.getX();
		double velocityY = velocity.getY();
		
		if (Math.abs(velocityX) > getMaxVelocity().getX())
			velocityX = velocityX / Math.abs(velocityX) * getMaxVelocity().getX();

		if (Math.abs(velocityY) > getMaxVelocity().getY())
			velocityY = velocityY / Math.abs(velocityY) * getMaxVelocity().getY();
		
		if (velocityX < 0) {
			this.orientation = Orientation.Left;
		} else if (velocityX > 0) {
			this.orientation = Orientation.Right;
		}
		
		this.velocity = new Point2D(velocityX, velocityY);
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
