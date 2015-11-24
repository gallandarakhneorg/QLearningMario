package fr.utbm.tc.qlearningmario.mario.entity;

import fr.utbm.tc.qlearningmario.mario.common.Hitbox;
import javafx.geometry.Point2D;

public class PirahnaPlant extends Enemy {
	public static final Point2D maxVelocity = new Point2D(0, 2);
	
	private static final Hitbox hitbox = new Hitbox(1, 1.5);
	
    public PirahnaPlant() {
        this.currentHitbox = PirahnaPlant.hitbox;
        setMaxVelocity(PirahnaPlant.maxVelocity);
    }
}
