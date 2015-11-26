package fr.utbm.tc.qlearningmario.mario.entity;

import fr.utbm.tc.qlearningmario.mario.common.Hitbox;
import javafx.geometry.Point2D;
import org.arakhne.afc.vmutil.locale.Locale;

public class Billball extends Enemy<Billball> {
	public static final Point2D maxVelocity;
	
	static {
        double x = Double.parseDouble(Locale.getString(Billball.class, "max.velocity.x")); //$NON-NLS-1$
        double y = Double.parseDouble(Locale.getString(Billball.class, "max.velocity.y")); //$NON-NLS-1$
        maxVelocity = new Point2D(x, y);
	}
	
	private static final Hitbox hitbox = new Hitbox(1, 1);
	
    public Billball() {
        this.currentHitbox = Billball.hitbox;
                
        setMaxVelocity(Billball.maxVelocity);
    }
}
