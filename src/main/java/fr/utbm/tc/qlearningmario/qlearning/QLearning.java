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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;

public class QLearning<Problem extends QProblem> {
	private final Random randomGenerator = new Random();
	private final Map<QState, Map<QAction, Float>> qValues = new TreeMap<>(new QStateNumberComparator());
	private final Problem problem;

	@SuppressWarnings("boxing")
	public QLearning(Problem problem) {
		this.problem = problem;

		for (QState state : problem.getStates()) {
			Map<QAction, Float> m = new TreeMap<>(new QActionNumberComparator());
			this.qValues.put(state, m);

			for(QAction action : problem.getActions(state)) {
				m.put(action, 0f);
			}
		}
	}

	public void learn(int numberOfIterations) {
		QState currentState;
		QAction action;
		QFeedback result;

		currentState = this.problem.getCurrentState();

		for(int i=0; i < numberOfIterations; ++i) {
			if (this.randomGenerator.nextFloat() < this.problem.getNu()) {
				currentState = this.problem.getRandomState();
			}

			if (this.randomGenerator.nextFloat() < this.problem.getRho()) {
				action = this.problem.getRandomAction(currentState);
			}
			else {
				action = getBestAction(currentState);
			}

			result = getFeedback(currentState, action);

			currentState = result.getNewState();
		}
	}

	private QFeedback getFeedback(QState state, QAction action) {
		QFeedback result = this.problem.takeAction(state, action);

		QAction bestNextAction = getBestAction(result.getNewState());
		float bestNextActionValue = getQValue(result.getNewState(), bestNextAction);

		float qValue = (1f - this.problem.getAlpha()) * getQValue(state, action) + this.problem.getAlpha() * (result.getScore() + this.problem.getGamma() * bestNextActionValue);

		setQValue(state, action, qValue);
		return result;
	}

	@SuppressWarnings("boxing")
	public QAction getBestAction(QState state) {
		Map<QAction,Float> qValuesState = this.qValues.get(state);
		List<QAction> bestActions = new ArrayList<>();
		float bestScore = Float.NEGATIVE_INFINITY;

		for (Entry<QAction,Float> entry : qValuesState.entrySet()) {
			if (entry.getValue() > bestScore) {
				bestScore = entry.getValue();

				bestActions.clear();
				bestActions.add(entry.getKey());
			}
			else if (entry.getValue() == bestScore) {
				bestActions.add(entry.getKey());
			}
		}

		return bestActions.get(this.randomGenerator.nextInt(bestActions.size()));
	}

	public float getQValue(QState state, QAction action) {
		Float qValue = this.qValues.get(state).get(action);
		if (qValue == null)
			return 0f;

		return qValue.floatValue();
	}

	@SuppressWarnings("boxing")
	private void setQValue(QState state, QAction action, float qValue) {
		this.qValues.get(state).put(action, qValue);
	}
}
