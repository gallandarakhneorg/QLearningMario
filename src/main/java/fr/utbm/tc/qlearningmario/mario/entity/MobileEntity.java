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

import fr.utbm.tc.qlearningmario.mario.common.Orientation;
import javafx.geometry.Point2D;

public class MobileEntity<T> extends Entity<T> {
	private Point2D velocity = Point2D.ZERO;
	private Point2D maxVelocity = new Point2D(3, 3);
	private Point2D maxAcceleration = new Point2D(3, 16);

	private boolean isOnGround = true;

	private Orientation orientation = Orientation.LEFT;

	public void setVelocity(Point2D velocity) {
		assert (velocity != null) : "You must give a non-null velocity object"; //$NON-NLS-1$
		double velocityX = velocity.getX();
		double velocityY = velocity.getY();

		if (Math.abs(velocityX) > getMaxVelocity().getX())
			velocityX = velocityX / Math.abs(velocityX) * getMaxVelocity().getX();

		if (Math.abs(velocityY) > getMaxVelocity().getY())
			velocityY = velocityY / Math.abs(velocityY) * getMaxVelocity().getY();

		// Update orientation.
		if (velocityX < 0) {
			this.orientation = Orientation.LEFT;
		} else if (velocityX > 0) {
			this.orientation = Orientation.RIGHT;
		}

		this.velocity = new Point2D(velocityX, velocityY);
	}

	public Point2D getVelocity() {
		return this.velocity;
	}

	public Point2D getMaxVelocity() {
		return this.maxVelocity;
	}

	public void setMaxVelocity(Point2D maxVelocity) {
		this.maxVelocity = maxVelocity;
	}

	public Point2D getMaxAcceleration() {
		return this.maxAcceleration;
	}

	public void setMaxAcceleration(Point2D maxAcceleration) {
		this.maxAcceleration = maxAcceleration;
	}

	void setOnGround(boolean isOnGround) {
		this.isOnGround = isOnGround;
	}

	public boolean isOnGround() {
		return this.isOnGround;
	}

	public Orientation getOrientation() {
		return this.orientation;
	}
}
