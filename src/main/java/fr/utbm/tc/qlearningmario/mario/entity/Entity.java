package fr.utbm.tc.qlearningmario.mario.entity;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import fr.utbm.tc.qlearningmario.mario.common.Hitbox;
import javafx.geometry.Point2D;

public class Entity<T> implements fr.utbm.tc.qlearningmario.mario.common.Cloneable<T> {
	private Point2D location = Point2D.ZERO; // 1.0 = 1 meter
	protected Hitbox currentHitbox = Hitbox.nullHitbox;

	private static int numberOfEntities = 0;
	private final int ID;

	public Entity() {
		this.ID = numberOfEntities;
		++numberOfEntities;
	}

	public int getID() {
		return this.ID;
	}

	public void setLocation(Point2D location) {
		this.location = location;
	}

	public Point2D getLocation() {
		return this.location;
	}

	public double distance(Entity<?> entity) {
		return sqrt(pow(this.location.getX() - entity.getLocation().getX(), 2)
				+ pow(this.location.getY() - entity.getLocation().getY(), 2));
	}

	public Hitbox getHitbox() {
		return this.currentHitbox;
	}

	public double getLeftBound() {
		return this.location.getX();
	}

	public double getRightBound() {
		return this.location.getX() + this.currentHitbox.getWidth();
	}

	public double getBottomBound() {
		return this.location.getY() + this.currentHitbox.getHeight();
	}

	public double getTopBound() {
		return this.location.getY();
	}

	public boolean collide(Point2D pt) {
		return this.location.getX() < pt.getX()
				&& this.location.getX() + this.currentHitbox.getWidth() > pt.getX()
				&& this.location.getY() < pt.getY()
				&& this.location.getY() + this.currentHitbox.getHeight() > pt.getY();
	}

	public boolean collide(Entity<?> entity) {
		return this.location.getX() < entity.getLocation().getX() + entity.getHitbox().getWidth()
				&& this.location.getX() + this.currentHitbox.getWidth() > entity.getLocation().getX()
				&& this.location.getY() + this.currentHitbox.getHeight() > entity.getLocation().getY()
				&& this.location.getY() < entity.getLocation().getY() + entity.getHitbox().getHeight();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T clone() {
		T o = null;
		try {
			o = ((T) super.clone());
		} catch(@SuppressWarnings("unused") CloneNotSupportedException cnse) {
			// Shouldn't happen since Cloneable is implemented.
		}

		return o;
	}
}
