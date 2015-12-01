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

import org.arakhne.afc.vmutil.locale.Locale;

import fr.utbm.tc.qlearningmario.mario.entity.MarioBody;
import fr.utbm.tc.qlearningmario.qlearning.QAction;
import fr.utbm.tc.qlearningmario.qlearning.QLearning;
import javafx.geometry.Point2D;

/** The MarioBody's corresponding agent.
 *
 * <p>It makes use of the Q-Learning engine.
 *
 * @author Benoît CORTIER
 * @mavengroupid fr.utbm.tc
 * @mavenartifactid QLearningMario
 */
public class MarioAgent extends Agent<MarioBody> {
	private static final int NB_LEARNING_ITERATIONS;

	static {
		NB_LEARNING_ITERATIONS = Integer.parseInt(Locale.getString(MarioAgent.class, "number.learning.iterations")); //$NON-NLS-1$
	}

	private MarioProblem problem = new MarioProblem();

	private QLearning<MarioProblem> qlearning = new QLearning<>(this.problem);

	/** Initialize the agent with the given MarioBody.
	 *
	 * @param body : a MarioBody.
	 */
	public MarioAgent(MarioBody body) {
		super(body);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void live() {
		super.live();

		this.problem.translateCurrentState(getBody(), getBody().getPerception());
		this.qlearning.learn(NB_LEARNING_ITERATIONS);

		QAction qAction = this.qlearning.getBestAction(this.problem.getCurrentState());
		MarioProblem.Action action = MarioProblem.Action.fromQAction(qAction);

		if (action == MarioProblem.Action.JUMP) {
			getBody().askAcceleration(new Point2D(0, -getBody().getMaxAcceleration().getY()));
		} else if (action == MarioProblem.Action.MOVE_LEFT) {
			getBody().askAcceleration(new Point2D(-getBody().getMaxAcceleration().getX(), 0));
		} else if (action == MarioProblem.Action.MOVE_RIGHT) {
			getBody().askAcceleration(new Point2D(getBody().getMaxAcceleration().getX(), 0));
		} else {
			getBody().askAcceleration(new Point2D(-getBody().getVelocity().getX(), 0));
		}
	}
}
