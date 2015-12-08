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

public class MarioProblem implements QProblem {

	private static final long serialVersionUID = -989872950159367594L;

	public enum Action {
		NONE(0),
		MOVE_RIGHT(1),
		MOVE_LEFT(2),
		JUMP(3);

		private int value;

		private Action(int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value;
		}

		public static Action fromQAction(QAction qAction) {
			assert(qAction.toInt() >= 0 && qAction.toInt() < Action.values().length);
			return Action.values()[qAction.toInt()];
		}
	}

	public enum SquareState {
		VOID(0),
		SOLID(1),
		ENEMY(2);

		private int value;

		private SquareState(int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value;
		}

		public static SquareState fromEntity(Entity<?> entity) {
			if (entity instanceof Solid) {
				return SquareState.SOLID;
			} else if (entity instanceof Enemy) {
				return SquareState.ENEMY;
			} else {
				return SquareState.VOID;
			}
		}

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

	private static final int nbSquaresPerState = 10;

	private final Random randomGenerator = new Random();

	/** Current state in the learning algorithm. */
	private QState currentState;

	private final QState[] states = new QState[((int) Math.pow(SquareState.values().length, nbSquaresPerState + 1)) - 1];
	private final QAction[] actions = new QAction[Action.values().length];

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
			if (sStates.get(4) == SquareState.VOID) {
				if (sStates.get(7) == SquareState.VOID) {
					score = FeedbackScore.BAD.getValue();
				} else {
					score = FeedbackScore.SLIGHT_BAD.getValue();
				}

				// Mario bottom line
				sStates.set(9, sStates.get(8));
				sStates.set(8, SquareState.fromId(this.randomGenerator.nextInt(SquareState.values().length - 1)));
				sStates.set(7, SquareState.fromId(this.randomGenerator.nextInt(SquareState.values().length - 1)));

				// Mario line
				sStates.set(6, sStates.get(5));
				sStates.set(5, SquareState.VOID);
				sStates.set(4, sStates.get(3));
				sStates.set(3, SquareState.fromId(this.randomGenerator.nextInt(SquareState.values().length - 1)));

				// Mario top line
				sStates.set(2, sStates.get(1));
				sStates.set(1, sStates.get(0));
				sStates.set(0, SquareState.fromId(this.randomGenerator.nextInt(SquareState.values().length - 1)));
			} else if (sStates.get(4) == SquareState.SOLID) {
				if (sStates.get(5) == SquareState.ENEMY || sStates.get(1) == SquareState.ENEMY) {
					score = FeedbackScore.BAD.getValue();
				}
			} else { // Enemy on the left.
				score = FeedbackScore.VERY_BAD.getValue();
			}
			break;
		case MOVE_RIGHT:
			if (sStates.get(5) == SquareState.VOID) {
				if (sStates.get(8) == SquareState.VOID) {
					if (sStates.get(9) == SquareState.VOID) {
						score = FeedbackScore.BAD.getValue();
					} else {
						score = FeedbackScore.VERY_BAD.getValue();
					}
				} else {
					score = FeedbackScore.SLIGHT_GOOD.getValue();
				}

				// Mario bottom line
				sStates.set(7, SquareState.fromId(this.randomGenerator.nextInt(SquareState.values().length - 1)));
				sStates.set(8, sStates.get(9));
				sStates.set(9, SquareState.fromId(this.randomGenerator.nextInt(SquareState.values().length - 1)));

				// Mario line
				sStates.set(3, sStates.get(4));
				sStates.set(4, SquareState.VOID);
				sStates.set(5, sStates.get(6));
				sStates.set(6, SquareState.fromId(this.randomGenerator.nextInt(SquareState.values().length - 1)));

				// Mario top line
				sStates.set(0, sStates.get(1));
				sStates.set(1, sStates.get(2));
				sStates.set(2, SquareState.fromId(this.randomGenerator.nextInt(SquareState.values().length - 1)));
			} else if (sStates.get(5) == SquareState.SOLID) {
				if (sStates.get(4) == SquareState.ENEMY || sStates.get(1) == SquareState.ENEMY) {
					score = FeedbackScore.BAD.getValue();
				}
			} else { // Enemy on the right.
				score = FeedbackScore.VERY_BAD.getValue();
			}
			break;
		case JUMP:
			if (sStates.get(1) == SquareState.VOID) {
				if (sStates.get(4) == SquareState.ENEMY || sStates.get(5) == SquareState.ENEMY) {
					score = FeedbackScore.SLIGHT_GOOD.getValue();
				}

				// Mario left column
				sStates.set(7, sStates.get(4));
				sStates.set(4, sStates.get(0));
				sStates.set(3, sStates.get(0));
				sStates.set(0, SquareState.fromId(this.randomGenerator.nextInt(SquareState.values().length - 1)));

				// Mario column
				sStates.set(1, SquareState.fromId(this.randomGenerator.nextInt(SquareState.values().length - 1)));

				// Mario right column
				sStates.set(9, sStates.get(6));
				sStates.set(8, sStates.get(5));
				sStates.set(5, sStates.get(2));
				sStates.set(6, sStates.get(2));
				sStates.set(2, SquareState.fromId(this.randomGenerator.nextInt(SquareState.values().length - 1)));
			} else if (sStates.get(1) == SquareState.SOLID) {
				if (sStates.get(4) == SquareState.ENEMY || sStates.get(5) == SquareState.ENEMY) {
					score = FeedbackScore.BAD.getValue();
				}
			} else { // Enemy on the top.
				score = FeedbackScore.VERY_BAD.getValue();
			}
			break;
		case NONE:
			if (sStates.get(5) == SquareState.ENEMY || sStates.get(4) == SquareState.ENEMY || sStates.get(1) == SquareState.ENEMY) {
				score = FeedbackScore.BAD.getValue();
			}
			break;
		default:
			break;
		}

		return new QFeedback(this.states[getQStateNumberFromSquareStates(sStates)], score);
	}

	public void translateCurrentState(MarioBody mario, List<Entity<?>> perception) {
		List<SquareState> sStates = new ArrayList<>(nbSquaresPerState);

		double[][] zones = new double[][] {
			{-3., -6., 3., 4.}, // x, y, width, height
			{0., -6., 1., 4.},
			{1., -6., 3., 4.},
			{-3., -2., 2., 2.},
			{-1., -2., 1., 2.},
			{1., -2., 1., 2.},
			{2., -2., 2., 2.},
			{-1., 0., 1., 5.},
			{1., 0., 1., 5.},
			{2., 0., 2., 5.}
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
		MarioProblem o = null;
		try {
			o = ((MarioProblem) super.clone());
		} catch(@SuppressWarnings("unused") CloneNotSupportedException cnse) {
			// Shouldn't happen since Cloneable is implemented.
		}

		return o;
	}
}
