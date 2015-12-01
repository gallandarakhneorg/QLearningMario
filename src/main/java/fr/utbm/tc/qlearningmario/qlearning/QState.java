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

package fr.utbm.tc.qlearningmario.qlearning;

/** Q-State, used to represent a state of the world.
 *
 * @author Jérôme BOULMIER, Benoît CORTIER
 * @mavengroupid fr.utbm.tc
 * @mavenartifactid QLearningMario
 */
public class QState extends Counter implements QBase {
	private static int numberOfQState;

	private static final long serialVersionUID = -1506512375211501871L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int toInt() {
		return getID();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public QState clone() {
		QState clone = null;
		try {
			clone = ((QState) super.clone());
		} catch (@SuppressWarnings("unused") CloneNotSupportedException cnse) {
			//
		}

		return clone;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int getNumberOfObject() {
		return numberOfQState;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void incrementNumberOfObject() {
		++numberOfQState;
	}
}
