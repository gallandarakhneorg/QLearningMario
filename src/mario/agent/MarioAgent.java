package mario.agent;

import javafx.geometry.Point2D;
import mario.entity.MarioBody;

public class MarioAgent extends Agent<MarioBody> {
	public MarioAgent(MarioBody body) {
		super(body);
	}

	@Override
	public void update() {
		getBody().askAcceleration(new Point2D(3, -20));
	}
}
