package mario.entity;

import javafx.geometry.Point2D;
import mario.common.Hitbox;

public class PirahnaPlant extends Enemy {
	public static final Point2D maxVelocity = new Point2D(0, 2);
	
	private static final Hitbox hitbox = new Hitbox(1, 1.5);
	
    public PirahnaPlant() {
        this.currentHitbox = PirahnaPlant.hitbox;
        setMaxVelocity(PirahnaPlant.maxVelocity);
    }
}
