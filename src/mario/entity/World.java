package mario.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

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
                agentBody.setPerception(getNearbyEntities(entity, agentBody.getPerceptionDistance()));
            }
        }
	}

    public List<Entity> getEntities() {
        return this.entities;
    }
    
	public List<Entity> getNearbyEntities(Entity entity, double distance) {
        List<Entity> nearbyEntities = new ArrayList<>();
        for (Entity otherEntity : this.entities) {
            if (entity.distance(otherEntity) < distance)
                nearbyEntities.add(otherEntity);
        }
        
        nearbyEntities.remove(entity);
        
        return nearbyEntities;
	}

	public void update() {
		for (Entity entity : this.entities) {
			if (entity instanceof MobileEntity)
				updateMobileEntity((MobileEntity)entity);
		}
		
		notifyObservers(this.entities);
	}
	
	public void addEntity(Entity entity) {
	    this.entities.add(entity);
	    notifyObservers(entity);
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
		
		List<Entity> entitiesOnWay = getEntitiesOnTheWay(mobileEntity);
		// TODO: prevent the mobileEntity from crossing Solids. 
		
		mobileEntity.setLocation(new Point2D(mobileEntity.getLocation().getX() + mobileEntity.getVelocity().getX() / this.updatesPerSecond,
											 mobileEntity.getLocation().getY() + mobileEntity.getVelocity().getY() / this.updatesPerSecond));
	}

	@SuppressWarnings("boxing")
    private List<Entity> getEntitiesOnTheWay(MobileEntity entity) {
	    double positionX = entity.getLocation().getX();
	    double positionY = entity.getLocation().getY();
        double newPositionX = positionX + entity.getVelocity().getX() / this.updatesPerSecond;
        double newPositionY = positionY + entity.getVelocity().getY() / this.updatesPerSecond;


        Polygon polygon = new Polygon();

        double Left = Math.min(positionX, newPositionX);
        double Right = Math.max(positionX, newPositionX) + entity.getHitbox().getWidth();
        double Down = Math.min(positionY, newPositionY) + entity.getHitbox().getHeight();
        double Top = Math.max(positionY, newPositionY);
  
        if (positionY < newPositionY && positionX > newPositionX
                || positionY > newPositionY && positionX < newPositionX) {
            polygon.getPoints().addAll(new Double[]{
                    Left, Down - entity.getHitbox().getWidth(),
                    Right - entity.getHitbox().getWidth(), Top,
                    Right, Top,
                    Right, Top + entity.getHitbox().getHeight(),
                    Left + entity.getHitbox().getWidth(), Down,
                    Left, Down
            });
        } else {
            polygon.getPoints().addAll(new Double[]{
                    Left, Top,
                    Left + entity.getHitbox().getWidth(), Top,
                    Right, Down - entity.getHitbox().getHeight(),
                    Right, Down,
                    Right - entity.getHitbox().getWidth(), Down,
                    Left, Top + entity.getHitbox().getHeight()});
           }
        
        List<Entity> nearbyEntities = getNearbyEntities(entity,
        		entity.getLocation().distance(newPositionX + entity.getHitbox().getWidth(),
        				newPositionY + entity.getHitbox().getHeight()));
        
        int i = 0;
        while (i < nearbyEntities.size()) {
            if (nearbyEntities.get(i) instanceof Solid
                    && polygon.intersects(nearbyEntities.get(i).getLocation().getX(),
                            nearbyEntities.get(i).getLocation().getY(),
                            nearbyEntities.get(i).getHitbox().getWidth(),
                            nearbyEntities.get(i).getHitbox().getHeight())) {
                ++i;
            } else {
                nearbyEntities.remove(i);
            }
        }
        
        return nearbyEntities;
    }
}
