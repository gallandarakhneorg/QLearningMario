package fr.utbm.tc.qlearningmario.mario.entity;

import fr.utbm.tc.qlearningmario.mario.common.Hitbox;
import javafx.geometry.Point2D;

public class Goomba extends Enemy {
	//FIXME: Caution: making attribute public with an Object (here a Point2D) may be dangerous if setters are available.
	public static final Point2D maxVelocity = new Point2D(2.4f, 3f);
	public static final Point2D maxAcceleration = new Point2D(4.8f, 3f);
	
	private static final Hitbox hitbox = new Hitbox(1, 1);
	
    public Goomba() {
        this.currentHitbox = Goomba.hitbox;
        setMaxVelocity(Goomba.maxVelocity);
        setMaxAcceleration(Goomba.maxAcceleration);
    }
    
    @Override
    public double getPerceptionDistance() {
    	// FIXME: Replace "1.f" by "1." or "1" since the return type is double (avoid casts by the compilers)
    	return 1.f;
    }
}
