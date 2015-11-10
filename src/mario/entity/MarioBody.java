/**
 * 
 */
package mario.entity;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;
import mario.common.MarioAction;
import mario.common.MarioState;

public final class MarioBody extends MobileEntity implements AgentBody, Damageable {
	private int maxHealth = 3;
	private int currentHealth = 1;
	private int defaultHealth = 3;
	private MarioState state = MarioState.SmallMario;
	
	private double invincibilityTimestamp = 0f;
	
	private Point2D wantedMovement;
	private MarioAction wantedAction;

	private List<Entity> perception = new ArrayList<>();

	@Override
	public void damage(int amount) {
	    if (!isInvincible())
	        return;

		if (this.currentHealth < amount) {
			this.currentHealth = 0;
		} else {
		    this.currentHealth -= amount;
		}
		
		updateState();

	}

	@Override
	public void damage(int amount, Entity source) {
	    if (!isInvincible())
            return;

	    if (this.currentHealth < amount) {
            this.currentHealth = 0;  
        } else {
            this.currentHealth -= amount;
        }
        
        updateState();
	}
	
	@Override
    public void kill() {
        setHealth(0);
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
		this.maxHealth = this.defaultHealth;
	}

	@Override
	public void heal(int amount) {
		if (amount > 0) {
			this.currentHealth += amount;

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
	public List<Entity> getPerception() {
		return this.perception;
	}
	
	@Override
    public void setPerception(List<Entity> perception) {
	    this.perception = perception;
    }

    @Override
    public double getPerceptionDistance() {
        return 20f;
    }

	@Override
	public void move(Point2D vector) {
		this.wantedMovement = vector;
	}

	@Override
	public void act(int a) {
	    if (a > 0 && a < MarioAction.values().length)
	        this.wantedAction = MarioAction.values()[a];

	}
	
	public MarioState getState() {
		return this.state;
	}

	public void updateState() {
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

	public Point2D getWantedMovement() {
	    return this.wantedMovement;
	}
	
	public MarioAction getWantedAction() {
	    return this.wantedAction;
	}
}
