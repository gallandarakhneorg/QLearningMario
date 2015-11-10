package mario.entity;

import java.util.List;

import javafx.geometry.Point2D;
import mario.common.Orientation;

public class Entity {
	Point2D location;
	Orientation orientation;
	
	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}
	public void setLocation(Point2D location) {
		this.location = location;
	}

	public Point2D getLocation() {
		return this.location;
	}
	
	public Orientation getOrientation() {
		return this.orientation;
	}
	
	public List<Entity> getNearbyEntities() {
		return null;
	}
}
