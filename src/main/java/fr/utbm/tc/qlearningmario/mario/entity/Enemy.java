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
import java.util.List;

import org.arakhne.afc.vmutil.locale.Locale;

import fr.utbm.tc.qlearningmario.mario.agent.Body;
import javafx.geometry.Point2D;

public class Enemy<T> extends MobileEntity<T> implements Damageable, AgentBody, Body {
	private int defaultMaxHealth = Integer.parseInt(Locale.getString(Enemy.class, "default.max.health")); //$NON-NLS-1$
	private int maxHealth = this.defaultMaxHealth;
	private int currentHealth = this.maxHealth;

	private double invincibilityTimestamp = 0f;

	private Point2D wantedAcceleration = Point2D.ZERO;

	private List<Entity<?>> perception = new ArrayList<>();

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
		return 0f;
	}

	@Override
	public void askAcceleration(Point2D vector) {
		this.wantedAcceleration = vector;
	}

	@Override
	public void askAction(int action) {
		// A basic enemy does nothing.
	}

	@Override
	public Point2D getWantedAcceleration() {
		return this.wantedAcceleration;
	}

	@Override
	public int getWantedAction() {
		return 0; // An enemy doesn't want to act.
	}

	@Override
	public void damage(int amount) {
		if (!isInvincible())
			return;

		if (this.currentHealth < amount) {
			this.currentHealth = 0;
		} else {
			this.currentHealth -= amount;
		}
	}

	@Override
	public void damage(int amount, Entity<?> source) {
		if (!isInvincible())
			return;

		if (this.currentHealth < amount) {
			this.currentHealth = 0;
		} else {
			this.currentHealth -= amount;
		}
	}

	@Override
	public void kill() {
		this.currentHealth = 0;
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
		}

	}

	@Override
	public int getMaxHealth() {
		return this.maxHealth;
	}

	@Override
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
		if (this.currentHealth > this.maxHealth) {
			this.currentHealth = this.maxHealth;
		}
	}

	@Override
	public void resetMaxHealth() {
		this.maxHealth = this.defaultMaxHealth;
		if (this.currentHealth > this.maxHealth) {
			this.currentHealth = this.maxHealth;
		}

	}

	@Override
	public void heal(int amount) {
		if (amount > 0) {
			this.currentHealth += amount;
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
}
