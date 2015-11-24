package mario.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

public class World extends Observable {
	private List<Entity> entities = new ArrayList<>();
	private int updatesPerSecond = 60;
	private double gravity = 0.5;

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
		
		setChanged();
		notifyObservers(this.entities);
	}
	
	public void addEntity(Entity entity) {
	    this.entities.add(entity);
	    
	    setChanged();
	    notifyObservers(entity);
	}
	
	private void updateMobileEntity(MobileEntity mobileEntity) {
		double accelerationX, accelerationY, speedX, speedY, movementX, movementY;

		if (mobileEntity instanceof AgentBody) {
        	AgentBody agentBody = (AgentBody)mobileEntity;
        	
        	if (mobileEntity.isOnGround()) {
        		accelerationX = agentBody.getWantedAcceleration().getX();
        		accelerationY = agentBody.getWantedAcceleration().getY() + this.gravity;
        	} else {
        		accelerationX = agentBody.getWantedAcceleration().getX() / 100;
        		accelerationY = this.gravity;
        	}
        	

        	if (Math.abs(accelerationX) > mobileEntity.getMaxAcceleration().getX())
				accelerationX = accelerationX / Math.abs(accelerationX) * mobileEntity.getMaxAcceleration().getX();
			
			if (Math.abs(accelerationY) > mobileEntity.getMaxAcceleration().getY())
				accelerationY = accelerationY / Math.abs(accelerationY) * mobileEntity.getMaxAcceleration().getY();

        	speedX = mobileEntity.getVelocity().getX() + accelerationX;
        	speedY = mobileEntity.getVelocity().getY() + accelerationY;
        	
        	mobileEntity.setVelocity(new Point2D(speedX, speedY));

        	movementX = speedX / this.updatesPerSecond;
        	movementY = speedY / this.updatesPerSecond;
        	
        	agentBody.askAcceleration(Point2D.ZERO);

        } else {
        	accelerationX = 0;
        	accelerationY = this.gravity;
        	
        	speedX = mobileEntity.getVelocity().getX() + accelerationX;
        	speedY = mobileEntity.getVelocity().getY() + accelerationY;
        	
        	movementX = speedX / this.updatesPerSecond;
        	movementY = speedY / this.updatesPerSecond;
        }
        
		mobileEntity.setOnGround(false);
		
        List<Entity> entityOnTheWay = getEntitiesOnTheWay(mobileEntity);

        for (Entity entity : entityOnTheWay) {
			if (segmentIntersect(mobileEntity.getLeftBound(), mobileEntity.getRightBound(),
					entity.getLeftBound(), entity.getRightBound())) {
				if (speedY > 0) {
					if (mobileEntity.getBottomBound() - entity.getTopBound() < movementY) {
						movementY = mobileEntity.getBottomBound() - entity.getTopBound();
						if (movementY > 0) {
							movementY = - movementY;
						} else {
							movementY = 0;
							mobileEntity.setOnGround(true);
						}

						speedY = 0;
					}
				} else {
					if (Math.abs(entity.getBottomBound() - mobileEntity.getTopBound()) < Math.abs(movementY)) {
						movementY = entity.getBottomBound() - mobileEntity.getTopBound();
						speedY = 0;
					}
				}
				mobileEntity.setVelocity(new Point2D(speedX, speedY));
			}

			if (segmentIntersect(mobileEntity.getTopBound(), mobileEntity.getBottomBound(), entity.getTopBound(), entity.getBottomBound())) {
				if (speedX > 0) {
					if (Math.abs(entity.getLeftBound() - mobileEntity.getRightBound()) < Math.abs(movementX)) {
						movementX = entity.getLeftBound() - mobileEntity.getRightBound();
						speedX = 0;
					}
				} else {
					if (Math.abs(mobileEntity.getLeftBound() - entity.getRightBound()) < Math.abs(movementX)) {
						movementX = mobileEntity.getLeftBound() - entity.getRightBound();
						if (movementX > 0) {
							movementX = - movementX;
						} else {
							movementX = 0;
						}

						speedX = 0;
					}
				}

				mobileEntity.setVelocity(new Point2D(speedX, speedY));
			}
		}
        
        mobileEntity.setLocation(new Point2D(mobileEntity.getLocation().getX() + movementX, mobileEntity.getLocation().getY() + movementY));
	}
	
	private static boolean segmentIntersect(double x1, double x2, double y1, double y2) {
		return x1 < y2 && x2 > y1;
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

