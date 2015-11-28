package fr.utbm.tc.qlearningmario.mario.agent;

import fr.utbm.tc.qlearningmario.mario.entity.MarioBody;
import javafx.geometry.Point2D;

public class MarioAgent extends Agent<MarioBody> {
	public MarioAgent(MarioBody body) {
		super(body);
	}

	@Override
	public void live() {
		super.live();

		getBody().askAcceleration(new Point2D(3, -20));
	}
}
