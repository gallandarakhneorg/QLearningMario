package fr.utbm.tc.qlearningmario.mario.agent;

import fr.utbm.tc.qlearningmario.mario.common.Orientation;
import fr.utbm.tc.qlearningmario.mario.entity.Enemy;
import fr.utbm.tc.qlearningmario.mario.entity.Entity;
import fr.utbm.tc.qlearningmario.mario.entity.Goomba;
import javafx.geometry.Point2D;

public class GoombaAgent extends Agent<Goomba> {
    public GoombaAgent(Goomba body) {
		super(body);
	}

	@Override
    public void live() {
		super.live();

        if (getBody().getVelocity().getX() == 0.f) {
        	if (getBody().getOrientation() == Orientation.LEFT) {
        		getBody().askAcceleration(new Point2D(getBody().getMaxAcceleration().getX(), 0));
        	} else {
        		getBody().askAcceleration(new Point2D(-getBody().getMaxAcceleration().getX(), 0));
        	}
            return;
        }
        for (Entity<?> entity : getBody().getPerception()) {
            if (entity instanceof Enemy && getBody().collide(entity)) {
            	if (entity.getLocation().getX() < getBody().getLocation().getX()) {
            		getBody().askAcceleration(new Point2D(getBody().getMaxAcceleration().getX(), 0));
            	} else {
            		getBody().askAcceleration(new Point2D(-getBody().getMaxAcceleration().getX(), 0));
            	}
            }
        }
    }
}
