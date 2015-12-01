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

import javafx.geometry.Point2D;

/** Agent abstract class.
 *
 * @param <B> : is the type of body.
 * @author Jérôme BOULMIER, Benoît CORTIER
 * @mavengroupid fr.utbm.tc
 * @mavenartifactid QLearningMario
 */
public abstract class Agent<B extends Body> {
	private final B body;

	/** Initialize the agent with the given body.
	 *
	 * @param body : the agent's body.
	 */
	public Agent(B body) {
		assert (body != null);
		this.body = body;
	}

	/** Makes the agent live.
	 * By default this function reset the wanted acceleration to zero.
	 * Should be overridden in superclasses.
	 */
	public void live() {
		this.body.askAcceleration(Point2D.ZERO);
	}

	/** Return the agent's body.
	 *
	 * @return the body.
	 */
	protected final B getBody() {
		return this.body;
	}
}
