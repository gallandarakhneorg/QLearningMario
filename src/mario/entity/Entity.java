package mario.entity;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import javafx.geometry.Point2D;
import mario.common.Hitbox;

public class Entity {
	private Point2D location; // 1.f = 1 meter
	protected Hitbox currentHitbox = Hitbox.nullHitbox;

	public void setLocation(Point2D location) {
		this.location = location;
	}

	public Point2D getLocation() {
		return this.location;
	}

    public double distance(Entity entity) {
        return sqrt(pow(this.location.getX() - entity.getLocation().getX(), 2)
                + pow(this.location.getY() - entity.getLocation().getY(), 2));
    }
    
    public Hitbox getHitbox() {
    	// Update Hitbox location.
    	this.currentHitbox.setX(this.location.getX());
    	this.currentHitbox.setY(this.location.getY());
    	
    	return this.currentHitbox;
    }
}
