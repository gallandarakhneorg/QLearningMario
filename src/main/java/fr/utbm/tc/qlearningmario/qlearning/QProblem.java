package fr.utbm.tc.qlearningmario.qlearning;

import java.io.Serializable;
import java.util.List;

public interface QProblem extends Cloneable, Serializable {

	public float getAlpha();

	public float getGamma();

	public float getRho();

	public float getNu();

	public List<QState> getStates();

	public QState getCurrentState();

	public QState getRandomState();

	public List<QAction> getActions(QState state);

	public QAction getRandomAction(QState state);

	public QFeedback takeAction(QState state, QAction action);
}
