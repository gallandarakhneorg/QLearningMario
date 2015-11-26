package fr.utbm.tc.qlearningmario.mario.entity;

import org.arakhne.afc.vmutil.locale.Locale;

import fr.utbm.tc.qlearningmario.mario.common.Hitbox;
import javafx.geometry.Point2D;

public class Goomba extends Enemy<Goomba> {
	static final Point2D maxVelocity;
	static final Point2D maxAcceleration;
	
	static {
		double maxVelocityX = Double.parseDouble(Locale.getString(Goomba.class, "max.velocity.x")); //$NON-NLS-1$
		double maxVelocityY = Double.parseDouble(Locale.getString(Goomba.class, "max.velocity.y")); //$NON-NLS-1$
		maxVelocity = new Point2D(maxVelocityX, maxVelocityY);
		
		double maxAccelerationX = Double.parseDouble(Locale.getString(Goomba.class, "max.acceleration.x")); //$NON-NLS-1$
		double maxAccelerationY = Double.parseDouble(Locale.getString(Goomba.class, "max.acceleration.y")); //$NON-NLS-1$
		maxAcceleration = new Point2D(maxAccelerationX, maxAccelerationY);
	}
	
	private static final Hitbox hitbox = new Hitbox(1, 1);
	
    public Goomba() {
        this.currentHitbox = Goomba.hitbox;
        setMaxVelocity(Goomba.maxVelocity);
        setMaxAcceleration(Goomba.maxAcceleration);
    }
    
    @Override
    public double getPerceptionDistance() {
    	return 1.;
    }
}
