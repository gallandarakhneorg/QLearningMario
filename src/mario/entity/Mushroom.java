package mario.entity;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;
import mario.common.Hitbox;

public class Mushroom extends MobileEntity implements AgentBody, Collectable {
	public static final Point2D maxVelocity = new Point2D(2.4, 3);
	
	private static final Hitbox hitbox = new Hitbox(1, 1);
	
    private boolean isCollected = false;
    private Entity collector;
    private List<Entity> perception = new ArrayList<>();
    private Point2D wantedAcceleration;

    public Mushroom() {
        this.currentHitbox = Mushroom.hitbox;
        setMaxVelocity(Mushroom.maxVelocity);
    }
    
    @Override
    public void collect(Entity entity) {
        this.isCollected = true;
        this.collector = entity;
    }

    @Override
    public boolean isCollected() {
        return this.isCollected;
    }

    @Override
    public Entity getCollector() {
        return this.collector;
    }

    @Override
    public List<Entity> getPerception() {
        return this.perception;
    }

    @Override
    public void setPerception(List<Entity> perception) {
        this.perception = perception;
    }

    @Override
    public double getPerceptionDistance() {
        return 0f;
    }

    @Override
    public void askAcceleration(Point2D vector) {
        this.wantedAcceleration = vector;
    }

    @Override
    public void askAction(int action) {
        // A mushroom doesn't have any brain.
    }

    @Override
    public Point2D getWantedAcceleration() {
        return this.wantedAcceleration;
    }

    @Override
    public int getWantedAction() {
        return 0;
    }

}
