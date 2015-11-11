/**
 * 
 */
package mario.entity;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;
import mario.common.Hitbox;
import mario.common.MarioAction;
import mario.common.MarioState;
import mario.common.Orientation;

public final class MarioBody extends MobileEntity implements AgentBody, Damageable {
	// Hitboxes
	static private final Hitbox smallHitbox = new Hitbox(1, 1);
	static private final Hitbox bigHitbox = new Hitbox(1, 2);
	
	private int maxHealth = 3;
	private int currentHealth = 1;
	private int defaultHealth = 3;
	private MarioState state = MarioState.SmallMario;
	
	private double invincibilityTimestamp = 0f;
	
	private Point2D wantedMovement;
	private MarioAction wantedAction;

	private List<Entity> perception = new ArrayList<>();
	
	public MarioBody() {
		this.currentHitbox = MarioBody.smallHitbox;
		setOrientation(Orientation.Right);
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
	
	@Override
    public Point2D getWantedMovement() {
        return this.wantedMovement;
    }
    
    @Override
    public int getWantedAction() {
        return this.wantedAction.ordinal();
    }
	
	public MarioState getState() {
		return this.state;
	}

	public void updateState() {
		if (this.currentHealth <= 0) {
		    this.state = MarioState.values()[0];
		} else if (this.currentHealth >= 3) {
		    this.state = MarioState.values()[3];
		} else {
		    this.state = MarioState.values()[this.currentHealth];
		}
		
		if (this.currentHealth == 1) {
			this.currentHitbox = MarioBody.smallHitbox;
		} else {
			this.currentHitbox = MarioBody.bigHitbox;
		}
	}
}
