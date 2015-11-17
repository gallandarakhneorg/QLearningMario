package mario.agent;

import java.util.List;

import javafx.geometry.Point2D;
import mario.entity.AgentBody;
import mario.entity.Enemy;
import mario.entity.Entity;
import mario.entity.Goomba;

public class GoombaAgent extends Agent<Goomba> {
    private Goomba body;

    public GoombaAgent(Goomba body) {
        this.body = body;
    }

    @Override
    public void update() {
        List<Entity> entities = this.body.getPerception();
        
        if (this.body.getVelocity() == Point2D.ZERO) {
            this.body.askAcceleration(new Point2D(-4.8f, 0));
            return;
        }

        for (Entity entity : entities) {
            if (entity instanceof Enemy && entity.collide(this.body)) {
                if (this.body.getVelocity().getX() < 0) {
                    this.body.askAcceleration(new Point2D(Goomba.maxAcceleration.getX(), 0f));
                } else {
                    this.body.askAcceleration(new Point2D(-Goomba.maxAcceleration.getX(), 0f));
                }
            }
        }
    }

    @Override
    public Goomba getBody() {
        return this.body;
    }

}
