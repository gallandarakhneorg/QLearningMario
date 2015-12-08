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
/**
 *
 */
package fr.utbm.tc.qlearningmario.mario.entity;

import java.util.ArrayList;
import java.util.List;

import org.arakhne.afc.vmutil.locale.Locale;

import fr.utbm.tc.qlearningmario.mario.agent.Body;
import fr.utbm.tc.qlearningmario.mario.common.Hitbox;
import fr.utbm.tc.qlearningmario.mario.common.MarioAction;
import fr.utbm.tc.qlearningmario.mario.common.MarioState;
import javafx.geometry.Point2D;

public final class MarioBody extends MobileEntity<MarioBody> implements AgentBody, Damageable, Body {
	public static final Point2D maxVelocity;

	// Hitboxes
	private static final Hitbox smallHitbox;
	private static final Hitbox bigHitbox;

	private static final int defaultHealth;
	private int maxHealth = defaultHealth;
	private int currentHealth = 1;
	private MarioState state = MarioState.SMALL_MARIO;
	private static final double perceptionDistance;

	private double invincibilityTimestamp = 0f;

	private Point2D wantedAcceleration = Point2D.ZERO;
	private MarioAction wantedAction = null;

	private List<Entity<?>> perception = new ArrayList<>();

	static {
		double x = Double.parseDouble(Locale.getString(MarioBody.class, "max.velocity.x")); //$NON-NLS-1$
		double y = Double.parseDouble(Locale.getString(MarioBody.class, "max.velocity.y")); //$NON-NLS-1$

		double smallHeight = Double.parseDouble(Locale.getString(MarioBody.class, "small.height")); //$NON-NLS-1$
		double smallWidth = Double.parseDouble(Locale.getString(MarioBody.class, "small.width")); //$NON-NLS-1$

		smallHitbox = new Hitbox(smallWidth, smallHeight);

		double bigHeight = Double.parseDouble(Locale.getString(MarioBody.class, "big.height")); //$NON-NLS-1$
		double bigWidth = Double.parseDouble(Locale.getString(MarioBody.class, "big.width")); //$NON-NLS-1$

		bigHitbox = new Hitbox(bigWidth, bigHeight);

		maxVelocity = new Point2D(x, y);
		defaultHealth = Integer.parseInt(Locale.getString(MarioBody.class, "default.health")); //$NON-NLS-1$

		perceptionDistance = Double.parseDouble(Locale.getString(MarioBody.class, "perception.distance")); //$NON-NLS-1$
	}

	public MarioBody() {
		this.currentHitbox = MarioBody.smallHitbox;
		setMaxVelocity(MarioBody.maxVelocity);
	}

	@Override
	public void damage(int amount) {
		if (isInvincible())
			return;

		if (this.currentHealth < amount) {
			this.currentHealth = 0;
		} else {
			this.currentHealth -= amount;
		}

		setNoDamageTimestamp(1);

		updateState();
	}

	@Override
	public void damage(int amount, Entity<?> source) {
		damage(amount);
	}

	@Override
	public void kill() {
		setHealth(0);
	}

	@Override
	public boolean isDead() {
		return this.currentHealth == 0;
	}

	@Override
	public int getHealth() {
		return this.currentHealth;
	}

	@Override
	public void setHealth(int health) {
		if (health >= 0 && health <= this.maxHealth) {
			this.currentHealth = health;

			updateState();
		}
	}

	@Override
	public int getMaxHealth() {
		return this.maxHealth;
	}

	@Override
	public void setMaxHealth(int maxHealth) {
		if (maxHealth > 0) {
			this.maxHealth = maxHealth;

			if (this.currentHealth > this.maxHealth) {
				this.currentHealth = this.maxHealth;

				updateState();
			}
		}
	}

	@Override
	public void resetMaxHealth() {
		this.maxHealth = defaultHealth;
	}

	@Override
	public void heal(int amount) {
		if (amount > 0) {
			this.currentHealth = Math.min(this.maxHealth, this.currentHealth + amount);

			updateState();
		}

	}

	@Override
	public boolean isInvincible() {
		if (this.invincibilityTimestamp > System.currentTimeMillis()/1000f) {
			return true;
		}

		return false;
	}

	@Override
	public double getNoDamageTimestamp() {
		return this.invincibilityTimestamp;
	}

	@Override
	public void setNoDamageTimestamp(double timestamp) {
		if (timestamp > System.currentTimeMillis()/1000f) {
			this.invincibilityTimestamp = timestamp;
		}
	}

	@Override
	public List<Entity<?>> getPerception() {
		return this.perception;
	}

	@Override
	public void setPerception(List<Entity<?>> perception) {
		this.perception = perception;
	}

	@Override
	public double getPerceptionDistance() {
		return perceptionDistance;
	}

	@Override
	public void askAcceleration(Point2D vector) {
		this.wantedAcceleration = vector;
	}

	@Override
	public void askAction(int a) {
		if (a > 0 && a < MarioAction.values().length)
			this.wantedAction = MarioAction.values()[a];

	}

	@Override
	public Point2D getWantedAcceleration() {
		return this.wantedAcceleration;
	}

	@Override
	public int getWantedAction() {
		return this.wantedAction.ordinal();
	}

	public MarioState getState() {
		return this.state;
	}

	private void updateState() {
		this.state = MarioState.fromHealth(this.currentHealth);

		if (this.currentHealth == 1) {
			this.currentHitbox = MarioBody.smallHitbox;
		} else {
			this.currentHitbox = MarioBody.bigHitbox;
		}
	}
}
