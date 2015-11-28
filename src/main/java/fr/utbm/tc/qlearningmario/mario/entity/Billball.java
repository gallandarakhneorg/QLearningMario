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
