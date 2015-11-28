package fr.utbm.tc.qlearningmario.mario.entity;

import org.arakhne.afc.vmutil.locale.Locale;

import fr.utbm.tc.qlearningmario.mario.common.Hitbox;
import javafx.geometry.Point2D;

public class PirahnaPlant extends Enemy<PirahnaPlant> {
	public static final Point2D maxVelocity;

	static {
		double x = Double.parseDouble(Locale.getString(PirahnaPlant.class, "max.velocity.x")); //$NON-NLS-1$
		double y = Double.parseDouble(Locale.getString(PirahnaPlant.class, "max.velocity.y")); //$NON-NLS-1$
		maxVelocity = new Point2D(x, y);
	}

	private static final Hitbox hitbox = new Hitbox(1, 1.5);

	public PirahnaPlant() {
		this.currentHitbox = PirahnaPlant.hitbox;
		setMaxVelocity(PirahnaPlant.maxVelocity);
	}
}
