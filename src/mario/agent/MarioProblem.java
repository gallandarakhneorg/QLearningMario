package mario.agent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import qlearning.QAction;
import qlearning.QFeedback;
import qlearning.QProblem;
import qlearning.QState;

public class MarioProblem implements QProblem {

	private static final long serialVersionUID = -989872950159367594L;

	private static final float VERY_GOOD_SCORE = 2f;
	private static final float GOOD_SCORE = 1f;
	private static final float IDDLE_SCORE = 0f;
	private static final float BAD_SCORE = -1f;
	private static final float VERY_BAD_SCORE = -2f;
	
	private final Random randomGenerator = new Random();
	
	/** Current state in the learning algorithm.
	 */
	private QState currentState = null;
	
	private final QState[] states = new QState[16];
	
	public MarioProblem() {
		for(int i=0; i<this.states.length; ++i) {
			this.states[i] = new QState();
		}
	}
	
	@Override
	public float getAlpha() {
		return .6f;
	}

	@Override
	public float getGamma() {
		return .6f;
	}

	@Override
	public float getRho() {
		return .2f;
	}

	@Override
	public float getNu() {
		return .3f;
	}

	@Override
	public List<QState> getStates() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QState getCurrentState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QState getRandomState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QAction> getActions(QState state) {
		List<QAction> actions = new ArrayList<QAction>();
		// TODO find actions
		return actions;
	}

	@Override
	public QAction getRandomAction(QState state) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QFeedback takeAction(QState state, QAction action) {
		// TODO Auto-generated method stub
		return null;
	}
	
    @Override
	public MarioProblem clone() {
    	MarioProblem o = null;
		try {
			o = ((MarioProblem) super.clone());
		} catch(CloneNotSupportedException cnse) {
			cnse.printStackTrace(System.err);
		}

		return o;
	}
}
