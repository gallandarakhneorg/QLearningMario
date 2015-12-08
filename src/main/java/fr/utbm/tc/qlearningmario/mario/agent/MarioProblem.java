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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import fr.utbm.tc.qlearningmario.mario.entity.Enemy;
import fr.utbm.tc.qlearningmario.mario.entity.Entity;
import fr.utbm.tc.qlearningmario.mario.entity.MarioBody;
import fr.utbm.tc.qlearningmario.mario.entity.Solid;
import fr.utbm.tc.qlearningmario.qlearning.QAction;
import fr.utbm.tc.qlearningmario.qlearning.QFeedback;
import fr.utbm.tc.qlearningmario.qlearning.QProblem;
import fr.utbm.tc.qlearningmario.qlearning.QState;

/** Define MarioProblem
 *
 * @author $Author: boulmier$
 * @author $Author: cortier$
 * @mavengroupid $GroupId$
 * @version $FullVersion$
 * @mavenartifactid $ArtifactId$
 */
public class MarioProblem implements QProblem {
	private static final int nbSquaresPerState = 10;

	private static final long serialVersionUID = -989872950159367594L;

	public enum SquarePosition {
		TOP_LEFT(0),
		TOP(1),
		TOP_RIGHT(2),
		EXTREME_LEFT(3),
		NEAR_LEFT(4),
		NEAR_RIGHT(5),
		EXTREME_RIGHT(6),
		NEAR_BOTTOM_LEFT(7),
		NEAR_BOTTOM_RIGHT(8),
		EXTREME_BOTTOM_RIGHT(9);

		private int value;

		/** Initialize value of a square.
		 *
		 * @param value : the value of the square
		 */
		private SquarePosition(int value) {
			this.value = value;
		}

		/** Return the value of a square.
		 *
		 * @return the value.
		 */
		public int getValue() {
			return this.value;
		}
	}

	/** Actions executable by Mario
	 *
	 * @author $Author: boulmier$
	 * @author $Author: cortier$
	 * @mavengroupid $GroupId$
	 * @version $FullVersion$
	 * @mavenartifactid $ArtifactId$
	 */
	public enum Action {
		NONE(0), // Do nothing
		MOVE_RIGHT(1),
		MOVE_LEFT(2),
		JUMP(3);

		private int value;

		/** Initialize value of an action.
		 *
		 * @param value : the value of the action
		 */
		private Action(int value) {
			this.value = value;
		}

		/** Return the value of an action.
		 *
		 * @return the value.
		 */
		public int getValue() {
			return this.value;
		}

		/**  Convert a qAction into a Mario action
		 *
		 * @param qAction : a qAction
		 * @return an action of Mario.
		 */
		public static Action fromQAction(QAction qAction) {
			assert(qAction.toInt() >= 0 && qAction.toInt() < Action.values().length);
			return Action.values()[qAction.toInt()];
		}
	}

	/** State available in each square.
	 *
	 * @author $Author: boulmier$
	 * @author $Author: cortier$
	 * @mavengroupid $GroupId$
	 * @version $FullVersion$
	 * @mavenartifactid $ArtifactId$
	 */
	public enum SquareState {
		VOID(0),
		SOLID(1),
		ENEMY(2);

		private int value;

		/** Initialize value of a square.
		 *
		 * @param value : the value of the square
		 */
		private SquareState(int value) {
			this.value = value;
		}

		/** Return the value of a square.
		 *
		 * @return the value.
		 */
		public int getValue() {
			return this.value;
		}

		/**  Convert a entity into a square state.
		 *
		 * @param entity : an entity
		 * @return a square state.
		 */
		public static SquareState fromEntity(Entity<?> entity) {
			if (entity instanceof Solid) {
				return SquareState.SOLID;
			} else if (entity instanceof Enemy) {
				return SquareState.ENEMY;
			} else {
				return SquareState.VOID;
			}
		}

		/** Convert an id into a square state.
		 *
		 * @param id : an id
		 * @return a square state.
		 */
		public static SquareState fromId(int id) {
			assert(id >= 0 && id < SquareState.values().length);
			return SquareState.values()[id];
		}
	}

	private enum FeedbackScore {
		VERY_GOOD(2f),
		GOOD(1f),
		SLIGHT_GOOD(0.5f),
		NEUTRAL(0f),
		SLIGHT_BAD(-0.5f),
		BAD(-1f),
		VERY_BAD(-2f);

		private float value;

		private FeedbackScore(float value) {
			this.value = value;
		}

		public float getValue() {
			return this.value;
		}
	}

	private final Random randomGenerator = new Random();

	// Current state in the learning algorithm.
	private QState currentState;

	private final QState[] states = new QState[((int) Math.pow(SquareState.values().length, nbSquaresPerState + 1)) - 1];

	private final QAction[] actions = new QAction[Action.values().length];

	/** Initialize all states and actions, the current state is initialized to the first value the first state.
	 */
	public MarioProblem() {
		for (int i = 0; i < this.states.length; ++i) {
			this.states[i] = new QState();
		}

		this.currentState = this.states[0];

		for (int i = 0; i < this.actions.length; ++i) {
			this.actions[i] = new QAction();
		}
	}

	@Override
	public float getAlpha() {
		return .5f;
	}

	@Override
	public float getGamma() {
		return .5f;
	}

	@Override
	public float getRho() {
		return .3f;
	}

	@Override
	public float getNu() {
		return .1f;
	}

	@Override
	public List<QState> getStates() {
		return Arrays.asList(this.states);
	}

	@Override
	public QState getCurrentState() {
		return this.currentState;
	}

	@Override
	public QState getRandomState() {
		return this.states[this.randomGenerator.nextInt(this.states.length - 1)];
	}

	@Override
	public List<QAction> getActions(QState state) {
		// All actions are always available.
		return Arrays.asList(this.actions);
	}

	@Override
	public QAction getRandomAction(QState state) {
		return this.actions[this.randomGenerator.nextInt(this.actions.length - 1)];
	}

	@Override
	public QFeedback takeAction(QState state, QAction action) {
		List<SquareState> sStates = getSquareStatesFromQState(state);

		float score = FeedbackScore.NEUTRAL.getValue();

		switch (Action.fromQAction(action)) {
		case MOVE_LEFT:
			if (sStates.get(SquarePosition.NEAR_LEFT.getValue()) == SquareState.VOID) {
				if (sStates.get(SquarePosition.NEAR_BOTTOM_LEFT.getValue()) == SquareState.VOID) {
					score = FeedbackScore.BAD.getValue();
				} else {
					score = FeedbackScore.SLIGHT_BAD.getValue();
				}

				// Mario bottom line
				sStates.set(SquarePosition.EXTREME_BOTTOM_RIGHT.getValue(), sStates.get(SquarePosition.NEAR_BOTTOM_RIGHT.getValue()));
				sStates.set(SquarePosition.NEAR_BOTTOM_RIGHT.getValue(), SquareState.fromId(this.randomGenerator.nextInt(SquareState.values().length - 1)));
				sStates.set(SquarePosition.NEAR_BOTTOM_LEFT.getValue(), SquareState.fromId(this.randomGenerator.nextInt(SquareState.values().length - 1)));

				// Mario line
				sStates.set(SquarePosition.EXTREME_RIGHT.getValue(), sStates.get(SquarePosition.NEAR_RIGHT.getValue()));
				sStates.set(SquarePosition.NEAR_RIGHT.getValue(), SquareState.VOID);
				sStates.set(SquarePosition.NEAR_LEFT.getValue(), sStates.get(SquarePosition.EXTREME_LEFT.getValue()));
				sStates.set(SquarePosition.EXTREME_LEFT.getValue(), SquareState.fromId(this.randomGenerator.nextInt(SquareState.values().length - 1)));

				// Mario top line
				sStates.set(SquarePosition.TOP_RIGHT.getValue(), sStates.get(SquarePosition.TOP.getValue()));
				sStates.set(SquarePosition.TOP.getValue(), sStates.get(SquarePosition.TOP_LEFT.getValue()));
				sStates.set(SquarePosition.TOP_LEFT.getValue(), SquareState.fromId(this.randomGenerator.nextInt(SquareState.values().length - 1)));
			} else if (sStates.get(SquarePosition.NEAR_LEFT.getValue()) == SquareState.SOLID) {
				if (sStates.get(SquarePosition.NEAR_RIGHT.getValue()) == SquareState.ENEMY || sStates.get(SquarePosition.TOP.getValue()) == SquareState.ENEMY) {
					score = FeedbackScore.BAD.getValue();
				}
			} else { // Enemy on the left.
				score = FeedbackScore.VERY_BAD.getValue();
			}
			break;
		case MOVE_RIGHT:
			if (sStates.get(SquarePosition.NEAR_RIGHT.getValue()) == SquareState.VOID) {
				if (sStates.get(SquarePosition.NEAR_BOTTOM_RIGHT.getValue()) == SquareState.VOID) {
					if (sStates.get(SquarePosition.EXTREME_BOTTOM_RIGHT.getValue()) == SquareState.VOID) {
						score = FeedbackScore.BAD.getValue();
					} else {
						score = FeedbackScore.VERY_BAD.getValue();
					}
				} else {
					score = FeedbackScore.SLIGHT_GOOD.getValue();
				}

				// Mario bottom line
				sStates.set(SquarePosition.NEAR_BOTTOM_LEFT.getValue(), SquareState.fromId(this.randomGenerator.nextInt(SquareState.values().length - 1)));
				sStates.set(SquarePosition.NEAR_BOTTOM_RIGHT.getValue(), sStates.get(SquarePosition.EXTREME_BOTTOM_RIGHT.getValue()));
				sStates.set(SquarePosition.EXTREME_BOTTOM_RIGHT.getValue(), SquareState.fromId(this.randomGenerator.nextInt(SquareState.values().length - 1)));

				// Mario line
				sStates.set(SquarePosition.EXTREME_LEFT.getValue(), sStates.get(SquarePosition.NEAR_LEFT.getValue()));
				sStates.set(SquarePosition.NEAR_LEFT.getValue(), SquareState.VOID);
				sStates.set(SquarePosition.NEAR_RIGHT.getValue(), sStates.get(SquarePosition.EXTREME_RIGHT.getValue()));
				sStates.set(SquarePosition.EXTREME_RIGHT.getValue(), SquareState.fromId(this.randomGenerator.nextInt(SquareState.values().length - 1)));

				// Mario top line
				sStates.set(SquarePosition.TOP_LEFT.getValue(), sStates.get(SquarePosition.TOP.getValue()));
				sStates.set(SquarePosition.TOP.getValue(), sStates.get(SquarePosition.TOP_RIGHT.getValue()));
				sStates.set(SquarePosition.TOP_RIGHT.getValue(), SquareState.fromId(this.randomGenerator.nextInt(SquareState.values().length - 1)));
			} else if (sStates.get(SquarePosition.NEAR_RIGHT.getValue()) == SquareState.SOLID) {
				if (sStates.get(SquarePosition.NEAR_LEFT.getValue()) == SquareState.ENEMY || sStates.get(SquarePosition.TOP.getValue()) == SquareState.ENEMY) {
					score = FeedbackScore.BAD.getValue();
				}
			} else { // Enemy on the right.
				score = FeedbackScore.VERY_BAD.getValue();
			}
			break;
		case JUMP:
			if (sStates.get(SquarePosition.TOP.getValue()) == SquareState.VOID) {
				if (sStates.get(SquarePosition.NEAR_LEFT.getValue()) == SquareState.ENEMY || sStates.get(SquarePosition.NEAR_RIGHT.getValue()) == SquareState.ENEMY) {
					score = FeedbackScore.SLIGHT_GOOD.getValue();
				}

				// Mario left column
				sStates.set(SquarePosition.NEAR_BOTTOM_LEFT.getValue(), sStates.get(SquarePosition.NEAR_LEFT.getValue()));
				sStates.set(SquarePosition.NEAR_LEFT.getValue(), sStates.get(SquarePosition.TOP_LEFT.getValue()));
				sStates.set(SquarePosition.EXTREME_LEFT.getValue(), sStates.get(SquarePosition.TOP_LEFT.getValue()));
				sStates.set(SquarePosition.TOP_LEFT.getValue(), SquareState.fromId(this.randomGenerator.nextInt(SquareState.values().length - 1)));

				// Mario column
				sStates.set(SquarePosition.TOP.getValue(), SquareState.fromId(this.randomGenerator.nextInt(SquareState.values().length - 1)));

				// Mario right column
				sStates.set(SquarePosition.EXTREME_BOTTOM_RIGHT.getValue(), sStates.get(SquarePosition.EXTREME_RIGHT.getValue()));
				sStates.set(SquarePosition.NEAR_BOTTOM_RIGHT.getValue(), sStates.get(SquarePosition.NEAR_RIGHT.getValue()));
				sStates.set(SquarePosition.NEAR_RIGHT.getValue(), sStates.get(SquarePosition.TOP_RIGHT.getValue()));
				sStates.set(SquarePosition.EXTREME_RIGHT.getValue(), sStates.get(SquarePosition.TOP_RIGHT.getValue()));
				sStates.set(SquarePosition.TOP_RIGHT.getValue(), SquareState.fromId(this.randomGenerator.nextInt(SquareState.values().length - 1)));
			} else if (sStates.get(SquarePosition.TOP.getValue()) == SquareState.SOLID) {
				if (sStates.get(SquarePosition.NEAR_LEFT.getValue()) == SquareState.ENEMY || sStates.get(SquarePosition.NEAR_RIGHT.getValue()) == SquareState.ENEMY) {
					score = FeedbackScore.BAD.getValue();
				}
			} else { // Enemy on the top.
				score = FeedbackScore.VERY_BAD.getValue();
			}
			break;
		case NONE:
			if (sStates.get(SquarePosition.NEAR_RIGHT.getValue()) == SquareState.ENEMY || sStates.get(SquarePosition.NEAR_LEFT.getValue()) == SquareState.ENEMY || sStates.get(1) == SquareState.ENEMY) {
				score = FeedbackScore.BAD.getValue();
			}
			break;
		default:
			break;
		}

		return new QFeedback(this.states[getQStateNumberFromSquareStates(sStates)], score);
	}

	/** Translate the current state of the world into a q-state understandable by the Q-Learning algorithm.
	 *
	 * @param mario : mario body.
	 * @param perception : perception of mario.
	 */
	public void translateCurrentState(MarioBody mario, List<Entity<?>> perception) {
		assert (mario != null && perception != null);

		List<SquareState> sStates = new ArrayList<>(nbSquaresPerState);

		double[][] zones = new double[][] {
			{-3., -6., 3., 4.}, // TOP_LEFT // x, y, width, height
			{0., -6., 1., 4.}, // TOP
			{1., -6., 3., 4.}, // TOP_RIGHT
			{-3., -2., 2., 2.}, // EXTREME_LEFT
			{-1., -2., 1., 2.}, // NEAR_LEFT
			{1., -2., 1., 2.}, // NEAR_RIGHT
			{2., -2., 2., 2.}, // EXTREME_RIGHT
			{-1., 0., 1., 5.}, // NEAR_BOTTOM_LEFT
			{1., 0., 1., 5.}, // NEAR_BOTTOM_RIGHT
			{2., 0., 2., 5.} // EXTREME_BOTTOM_RIGHT

		};

		Entity<?> nearest;

		for (int i = 0; i < zones.length; ++i) {
			nearest = getNearestEntityInZone(
					mario.getLocation().getX() + zones[i][0], mario.getLocation().getY() + zones[i][1] + mario.getHitbox().getHeight(),
					zones[i][2], zones[i][3],
					mario, perception);
			sStates.add(SquareState.fromEntity(nearest));

			if (nearest != null)
				perception.remove(nearest);
		}

		int stateNumber = getQStateNumberFromSquareStates(sStates);
		this.currentState = this.states[stateNumber];
	}

	private static boolean zoneContainsEntity(double ZoneX, double ZoneY,
			double ZoneWidth, double ZoneHeight,
			Entity<?> entity) {
		return ZoneX < entity.getLocation().getX() + entity.getHitbox().getWidth()
				&& ZoneX + ZoneWidth > entity.getLocation().getX()
				&& ZoneY + ZoneHeight > entity.getLocation().getY()
				&& ZoneY < entity.getLocation().getY() + entity.getHitbox().getHeight();
	}

	private static Entity<?> getNearestEntityInZone(double ZoneX, double ZoneY,
			double ZoneWidth, double ZoneHeight,
			MarioBody mario, List<Entity<?>> entities) {
		Entity<?> nearest = null;

		for (Entity<?> entity : entities) {
			if (zoneContainsEntity(ZoneX, ZoneY, ZoneWidth, ZoneHeight, entity)) {
				if (nearest == null || mario.distance(entity) < mario.distance(nearest)) {
					nearest = entity;
				}
			}
		}

		return nearest;
	}

	private static List<SquareState> getSquareStatesFromQState(QState state) {
		List<SquareState> sStates = new ArrayList<>(9);
		int stateNumber = state.toInt();

		for (int i = 0; i < nbSquaresPerState; ++i) {
			sStates.add(SquareState.fromId((stateNumber / (int) Math.pow(SquareState.values().length, i) % SquareState.values().length)));
		}

		return sStates;
	}

	private static int getQStateNumberFromSquareStates(List<SquareState> sStates) {
		int weight = 0;
		int stateNumber = 0;

		for (SquareState sState : sStates) {
			stateNumber += sState.getValue() * Math.pow(SquareState.values().length, weight);
			++weight;
		}

		return stateNumber;
	}

	@Override
	public MarioProblem clone() {
		MarioProblem clone = null;
		try {
			clone = ((MarioProblem) super.clone());
		} catch(@SuppressWarnings("unused") CloneNotSupportedException cnse) {
			// Shouldn't happen since Cloneable is implemented.
		}

		return clone;
	}
}
