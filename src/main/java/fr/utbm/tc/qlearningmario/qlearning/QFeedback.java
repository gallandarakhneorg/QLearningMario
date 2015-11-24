package fr.utbm.tc.qlearningmario.qlearning;

import java.io.Serializable;

public class QFeedback implements Cloneable, Serializable{

	private static final long serialVersionUID = 2250229614069647240L;
	
	private final float score;
	private final QState newState;
	
	public QFeedback(QState newState, float score) {
		this.newState = newState;
		this.score = score;
	}
	public float getScore() {
		return this.score;
	}
	
	public QState getNewState() {
		return this.newState;
	}
}
