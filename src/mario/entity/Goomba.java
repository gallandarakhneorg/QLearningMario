package mario.entity;

import javafx.geometry.Point2D;
import mario.common.Hitbox;

public class Goomba extends Enemy {
	public static final Point2D maxVelocity = new Point2D(2.4, 3);
	
	private static final Hitbox hitbox = new Hitbox(1, 1);
	
    public Goomba() {
        this.currentHitbox = Goomba.hitbox;
        setMaxVelocity(Goomba.maxVelocity);
    }
}
