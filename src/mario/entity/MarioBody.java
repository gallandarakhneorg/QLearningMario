/**
 * 
 */
package mario.entity;

import java.util.List;

import javafx.geometry.Point2D;
import mario.common.MarioState;

public final class MarioBody extends MobileEntity implements AgentBody, Damageable {
	private int maxHealth = 3;
	private int currentHealth = 1;
	private int defaultHealth = 3;
	private MarioState state = MarioState.SmallMario;

	@Override
	public void damage(int amount) {
		if (this.currentHealth < amount) {
			this.currentHealth -= amount;
			
			switch(this.currentHealth) {
			case 0:
				this.state = MarioState.DeadMario;
				break;
				
			case 1:
				this.state = MarioState.SmallMario;
				break;
			case 2:
				this.state = MarioState.SuperMario;
				break;
			case 3:
				this.state = MarioState.FireMario;
				break;
			default:
				break;
			}
		}

	}

	@Override
	public void damage(int amount, Entity source) {
		if (this.currentHealth < amount) {
			this.currentHealth -= amount;
			
			switch(this.currentHealth) {
			case 0:
				this.state = MarioState.DeadMario;
				break;
				
			case 1:
				this.state = MarioState.SmallMario;
				break;
			case 2:
				this.state = MarioState.SuperMario;
				break;
			case 3:
				this.state = MarioState.FireMario;
				break;
			default:
				break;
			}
		}
	}

	@Override
	public int getHealth() {
		return this.currentHealth;
	}

	@Override
	public void setHealth(int health) {
		if (health > 0 && health < this.maxHealth) {
			this.currentHealth = health;
			
			switch(this.currentHealth) {
			case 0:
				this.state = MarioState.DeadMario;
				break;
				
			case 1:
				this.state = MarioState.SmallMario;
				break;
			case 2:
				this.state = MarioState.SuperMario;
				break;
			case 3:
				this.state = MarioState.FireMario;
				break;
			default:
				break;
			}
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
		}

	}

	@Override
	public void resetMaxHealth() {
		this.maxHealth = this.defaultHealth;
	}

	@Override
	public void heal(int amount) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isInvincible() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setInvincible(boolean isInvincible) {
		// TODO Auto-generated method stub

	}

	@Override
	public double getNoDamageTimestamp() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setNoDamageTimestamp(double timestamp) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Entity> getPerception() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void move(Point2D vector) {
		// TODO Auto-generated method stub

	}

	@Override
	public void act(int action) {
		// TODO Auto-generated method stub

	}

}
