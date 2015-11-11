package mario.entity;

import javafx.geometry.Point2D;

import mario.common.Hitbox;

public class Goomba extends Enemy {
	public static final Point2D maxVelocity = new Point2D(2.4f, 3f);
	public static final Point2D maxAcceleration = new Point2D(4.8f, 3f);
	
	private static final Hitbox hitbox = new Hitbox(1, 1);
	
    public Goomba() {
        this.currentHitbox = Goomba.hitbox;
        setMaxVelocity(Goomba.maxVelocity);
        setMaxAcceleration(Goomba.maxAcceleration);
    }
}
