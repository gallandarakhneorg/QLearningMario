package mario.entity;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;
import mario.common.Hitbox;

public class Mushroom extends MobileEntity implements AgentBody, Collectable {
	private static final Hitbox hitbox = new Hitbox(1, 1);
	
    private boolean isCollected = false;
    private Entity collecter;
    private List<Entity> perception = new ArrayList<>();
    private Point2D wantedMovement;

    public Mushroom() {
        this.currentHitbox = Mushroom.hitbox; 
    }
    
    @Override
    public void collect(Entity entity) {
        this.isCollected = true;
        this.collecter = entity;
    }

    @Override
    public boolean isCollected() {
        return this.isCollected;
    }

    @Override
    public Entity getCollecter() {
        return this.collecter;
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
    public void move(Point2D vector) {
        this.wantedMovement = vector;
    }

    @Override
    public void act(int action) {
        // A mushroom doesn't have any brain.
    }

    @Override
    public Point2D getWantedMovement() {
        return this.wantedMovement;
    }

    @Override
    public int getWantedAction() {
        return 0;
    }

}
