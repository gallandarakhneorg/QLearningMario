package fr.utbm.tc.qlearningmario.mario.entity;

import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import fr.utbm.tc.qlearningmario.mario.common.Hitbox;
import javafx.geometry.Point2D;
import org.arakhne.afc.vmutil.Resources;
import org.arakhne.afc.vmutil.locale.Locale;

public class Billball extends Enemy {
	// FIXME: Remove. Prefer configuration file. See the static code.
	public static final Point2D maxVelocity;
	
	static {
        double x = Double.parseDouble(Locale.getString(Billball.class, "max.velocity.x"));
        double y = Double.parseDouble(Locale.getString(Billball.class, "max.velocity.y"));
        maxVelocity = new Point2D(x, y);
	}
	
	private static final Hitbox hitbox = new Hitbox(1, 1);
	
    public Billball() {
        this.currentHitbox = Billball.hitbox;
                
        setMaxVelocity(Billball.maxVelocity);
    }
}
