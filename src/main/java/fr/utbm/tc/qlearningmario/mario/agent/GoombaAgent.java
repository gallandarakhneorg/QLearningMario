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

package fr.utbm.tc.qlearningmario.mario.agent;

import fr.utbm.tc.qlearningmario.mario.common.Orientation;
import fr.utbm.tc.qlearningmario.mario.entity.Enemy;
import fr.utbm.tc.qlearningmario.mario.entity.Entity;
import fr.utbm.tc.qlearningmario.mario.entity.Goomba;
import javafx.geometry.Point2D;

/** Agent for Goomba
 *
 * @author $Author: boulmier$
 * @author $Author: cortier$
 * @mavengroupid $GroupId$
 * @version $FullVersion$
 * @mavenartifactid $ArtifactId$
 */
public class GoombaAgent extends Agent<Goomba> {
	/** Initialize the agent with a body.
	 *
	 * @param body : the body controlled by the agent.
	 */
	public GoombaAgent(Goomba body) {
		super(body);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void live() {
		super.live();

		if (getBody().getVelocity().getX() == 0.f) {
			if (getBody().getOrientation() == Orientation.LEFT) {
				getBody().askAcceleration(new Point2D(getBody().getMaxAcceleration().getX(), 0));
			} else {
				getBody().askAcceleration(new Point2D(-getBody().getMaxAcceleration().getX(), 0));
			}
			return;
		}
		for (Entity<?> entity : getBody().getPerception()) {
			if (entity instanceof Enemy && getBody().collide(entity)) {
				if (entity.getLocation().getX() < getBody().getLocation().getX()) {
					getBody().askAcceleration(new Point2D(getBody().getMaxAcceleration().getX(), 0));
				} else {
					getBody().askAcceleration(new Point2D(-getBody().getMaxAcceleration().getX(), 0));
				}
			}
		}
	}
}
