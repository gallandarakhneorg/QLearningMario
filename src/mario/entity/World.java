package mario.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javafx.geometry.Point2D;

public class World extends Observable {
	private List<Entity> entities = new ArrayList<>();
	private int updatesPerSecond = 60;
	private double gravity = 9.8;

	public void computePerceptions() {
        for (Entity entity : this.entities) {
            if (entity instanceof AgentBody)
            {
                AgentBody agentBody = ((AgentBody) entity);

                // Compute the AgentBody's perception.
                List<Entity> perception = new ArrayList<>();
                for (Entity otherEntity : this.entities) {
                    if (entity.distance(otherEntity) < agentBody.getPerceptionDistance())
                        perception.add(entity);
                }

                agentBody.setPerception(perception);
            }
        }
	}

    public List<Entity> getEntities() {
        return this.entities;
    }

	public void update() {
		for (Entity entity : this.entities) {
			if (entity instanceof MobileEntity)
				updateMobileEntity((MobileEntity)entity);
		}
	}
	
	private void updateMobileEntity(MobileEntity mobileEntity) {
		if (mobileEntity instanceof AgentBody) {
			AgentBody agentBody = (AgentBody)mobileEntity;
			double accelerationX = agentBody.getWantedAcceleration().getX();
			double accelerationY = agentBody.getWantedAcceleration().getY();
			
			if (Math.abs(accelerationX) > mobileEntity.getMaxAcceleration().getX())
				accelerationX = accelerationX / Math.abs(accelerationX) * mobileEntity.getMaxAcceleration().getX();
			
			if (Math.abs(accelerationY) > mobileEntity.getMaxAcceleration().getY())
				accelerationY = accelerationY / Math.abs(accelerationY) * mobileEntity.getMaxAcceleration().getY();
			
			mobileEntity.setVelocity(new Point2D(mobileEntity.getVelocity().getX() + accelerationX,
												 mobileEntity.getVelocity().getY() + accelerationY + this.gravity));
			
			// Reset agent's wanted acceleration to zero.
			agentBody.askAcceleration(Point2D.ZERO);
		} else {
			mobileEntity.setVelocity(new Point2D(mobileEntity.getVelocity().getX(),
												 mobileEntity.getVelocity().getY() + this.gravity));
		}
		
		// TODO: prevent the mobileEntity from crossing Solids. 
		
		mobileEntity.setLocation(new Point2D(mobileEntity.getLocation().getX() + mobileEntity.getVelocity().getX() / this.updatesPerSecond,
											 mobileEntity.getLocation().getY() + mobileEntity.getVelocity().getY() / this.updatesPerSecond));
	}

	public void addEntity(Entity entity) {
	    this.entities.add(entity);
	}
}
