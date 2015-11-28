/*******************************************************************************
 * Copyright (C) 2015 BOULMIER Jérôme, CORTIER Benoît
 *
 * This software is provided 'as-is', without any express or implied
 * warranty.  In no event will the authors be held liable for any damages
 * arising from the use of this software.
 *
 * Permission is granted to anyone to use this software for any purpose,
 * including commercial applications, and to alter it and redistribute it
 * freely, subject to the following restrictions:
 *
 * 1. The origin of this software must not be misrepresented; you must not
 *    claim that you wrote the original software. If you use this software
 *    in a product, an acknowledgment in the product documentation would be
 *    appreciated but is not required.
 * 2. Altered source versions must be plainly marked as such, and must not be
 *    misrepresented as being the original software.
 * 3. This notice may not be removed or altered from any source distribution.
 *******************************************************************************/
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
