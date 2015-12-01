/*******************************************************************************
 * Copyright (C) 2015 BOULMIER Jérôme, CORTIER Benoît
 *
 * This software is provided 'as-is', without any express or implied
 * warranty.  In no event will the authors be held liable for any damages
 * arising from the use of this software.
 *
 * Permission is granted to anyone to use this software for any purpose,
 * including commercial applications, and to alter it and redistribute it
 * freely, subject to the following restrictions:
 *
 * 1. The origin of this software must not be misrepresented; you must not
 *    claim that you wrote the original software. If you use this software
 *    in a product, an acknowledgment in the product documentation would be
 *    appreciated but is not required.
 * 2. Altered source versions must be plainly marked as such, and must not be
 *    misrepresented as being the original software.
 * 3. This notice may not be removed or altered from any source distribution.
 *******************************************************************************/

package fr.utbm.tc.qlearningmario.mario.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.arakhne.afc.vmutil.locale.Locale;

import fr.utbm.tc.qlearningmario.mario.entity.WorldEvent.Type;
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;

public class World {
	private static final int UPDATES_PER_SECOND;
	private List<Entity<?>> entities = new ArrayList<>();

	private static final double GRAVITY = Double.parseDouble(Locale.getString(World.class, "gravity")); //$NON-NLS-1$

	private final List<WorldListener> listeners = new ArrayList<>();

	static {
		UPDATES_PER_SECOND = Integer.parseInt(Locale.getString(World.class, "updates.per.second")); //$NON-NLS-1$
	}

	public void computePerceptions() {
		for (Entity<?> entity : this.entities) {
			if (entity instanceof AgentBody) {
				AgentBody agentBody = ((AgentBody) entity);

				// Compute the AgentBody's perception.
				agentBody.setPerception(getNearbyEntities(entity, agentBody.getPerceptionDistance()));
			}
		}
	}

	public List<Entity<?>> getEntities() {
		return this.entities;
	}

	public List<Entity<?>> getNearbyEntities(Entity<?> entity, double distance) {
		List<Entity<?>> nearbyEntities = new ArrayList<>();
		for (Entity<?> otherEntity : this.entities) {
			if (entity.distance(otherEntity) < distance)
				nearbyEntities.add(otherEntity);
		}

		nearbyEntities.remove(entity);

		return nearbyEntities;
	}

	public void update() {
		Iterator<Entity<?>> iterator = this.entities.iterator();
		while (iterator.hasNext()) {
			Entity<?> entity = iterator.next();

			if (entity instanceof Damageable && ((Damageable) entity).isDead()) {
				iterator.remove();
				fireEntityRemoved(entity);
			} else if (entity instanceof MobileEntity) {
				updateMobileEntity((MobileEntity<?>) entity);
			}
		}

		fireWorldUpdate();
	}

	public void addEntity(Entity<?> entity) {
		this.entities.add(entity);
		fireEntityAdded(entity);
	}

	private void updateMobileEntity(MobileEntity<?> mobileEntity) {
		double accelerationX, accelerationY = 0, speedX, speedY, movementX, movementY;

		if (mobileEntity instanceof AgentBody) {
			AgentBody agentBody = (AgentBody)mobileEntity;

			if (mobileEntity.isOnGround()) {
				accelerationX = agentBody.getWantedAcceleration().getX();
				accelerationY = agentBody.getWantedAcceleration().getY();
			} else {
				accelerationX = agentBody.getWantedAcceleration().getX() / 100;
			}

			accelerationY += GRAVITY;

			if (Math.abs(accelerationX) > mobileEntity.getMaxAcceleration().getX())
				accelerationX = accelerationX / Math.abs(accelerationX) * mobileEntity.getMaxAcceleration().getX();

			if (Math.abs(accelerationY) > mobileEntity.getMaxAcceleration().getY())
				accelerationY = accelerationY / Math.abs(accelerationY) * mobileEntity.getMaxAcceleration().getY();

			speedX = mobileEntity.getVelocity().getX() + accelerationX;
			speedY = mobileEntity.getVelocity().getY() + accelerationY;

			// Handle damages between Mario and the enemies.
			if (mobileEntity instanceof MarioBody) {
				for (Entity<?> entity : getNearbyEntities(mobileEntity,
						Math.max(mobileEntity.getHitbox().getHeight(),
								mobileEntity.getHitbox().getWidth()))) {
					if (entity instanceof Enemy) {
						if (mobileEntity.collide(entity)) {
							if (mobileEntity.getVelocity().getY() > 0) {
								((Enemy<?>) entity).damage(1, mobileEntity);
								speedY = -12.;
							} else {
								((MarioBody) mobileEntity).damage(1, entity);
							}
						}
					}
				}
			}

			mobileEntity.setVelocity(new Point2D(speedX, speedY));

			movementX = speedX / UPDATES_PER_SECOND;
			movementY = speedY / UPDATES_PER_SECOND;

		} else {
			accelerationX = 0;
			accelerationY = GRAVITY;

			speedX = mobileEntity.getVelocity().getX() + accelerationX;
			speedY = mobileEntity.getVelocity().getY() + accelerationY;

			movementX = speedX / UPDATES_PER_SECOND;
			movementY = speedY / UPDATES_PER_SECOND;
		}

		mobileEntity.setOnGround(false);

		List<Entity<?>> entityOnTheWay = getEntitiesOnTheWay(mobileEntity);

		for (Entity<?> entity : entityOnTheWay) {
			if (segmentIntersect(mobileEntity.getLeftBound(), mobileEntity.getRightBound(),
					entity.getLeftBound(), entity.getRightBound())) {
				if (speedY > 0) {
					if (mobileEntity.getBottomBound() - entity.getTopBound() < movementY) {
						movementY = mobileEntity.getBottomBound() - entity.getTopBound();
						if (movementY > 0) {
							movementY = -movementY;
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
	private List<Entity<?>> getEntitiesOnTheWay(MobileEntity<?> entity) {
		double positionX = entity.getLocation().getX();
		double positionY = entity.getLocation().getY();
		double newPositionX = positionX + entity.getVelocity().getX() / UPDATES_PER_SECOND;
		double newPositionY = positionY + entity.getVelocity().getY() / UPDATES_PER_SECOND;


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

		List<Entity<?>> nearbyEntities = getNearbyEntities(entity,
				entity.getLocation().distance(newPositionX + entity.getHitbox().getWidth(),
						newPositionY + entity.getHitbox().getHeight()));

		Iterator<Entity<?>> iterator = nearbyEntities.iterator();
		while (iterator.hasNext()) {
			Entity<?> currentEntity = iterator.next();

			if (!(currentEntity instanceof Solid)
					|| !(polygon.intersects(currentEntity.getLocation().getX(),
							currentEntity.getLocation().getY(),
							currentEntity.getHitbox().getWidth(),
							currentEntity.getHitbox().getHeight()))) {
				iterator.remove();
			}
		}

		return nearbyEntities;
	}

	public void addWorldListener(WorldListener worldListener) {
		assert(worldListener != null);
		this.listeners.add(worldListener);
	}

	public void removeWorldListener(WorldListener worldListener) {
		assert(worldListener != null);
		this.listeners.remove(worldListener);
	}

	private void fireEvent(WorldEvent e) {
		WorldListener[] tab = new WorldListener[this.listeners.size()];
		this.listeners.toArray(tab);

		for (WorldListener worldListener : tab) {
			worldListener.update(e);
		}
	}

	private void fireWorldUpdate() {
		WorldEvent e = new WorldEvent(this, Type.WORLD_UPDATE);
		fireEvent(e);
	}

	private void fireEntityAdded(Entity<?> entity) {
		WorldEvent e = new WorldEvent(this, entity, Type.ENTITY_ADDED);
		fireEvent(e);
	}

	private void fireEntityRemoved(Entity<?> entity) {
		WorldEvent e = new WorldEvent(this, entity, Type.ENTITY_REMOVED);
		fireEvent(e);
	}
}

