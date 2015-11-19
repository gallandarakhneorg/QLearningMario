package mario.agent;

import java.util.List;

import javafx.geometry.Point2D;
import mario.entity.Enemy;
import mario.entity.Entity;
import mario.entity.Goomba;

public class GoombaAgent extends Agent<Goomba> {
    public GoombaAgent(Goomba body) {
		super(body);
	}

	@Override
    public void update() {
        List<Entity> entities = getBody().getPerception();
        
        if (getBody().getVelocity() == Point2D.ZERO) {
        	getBody().askAcceleration(new Point2D(-4.8f, 0));
            return;
        }

        for (Entity entity : entities) {
            if (entity instanceof Enemy && entity.collide(getBody())) {
                if (getBody().getVelocity().getX() < 0) {
                	getBody().askAcceleration(new Point2D(Goomba.maxAcceleration.getX(), 0f));
                } else {
                	getBody().askAcceleration(new Point2D(-Goomba.maxAcceleration.getX(), 0f));
                }
            }
        }
    }
}
