package mario.entity;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;

public class Enemy extends MobileEntity implements Damageable, AgentBody {
    private int maxHealth = 1;
    private int currentHealth = 1;
    private int defaultHealth = 1;
    
    private double invincibilityTimestamp = 0f;
    
    private Point2D wantedMovement;

    private List<Entity> perception = new ArrayList<>();
  
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
        // A basic enemy does nothing.
    }
    
    @Override
    public Point2D getWantedMovement() {
        return this.wantedMovement;
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
    public void damage(int amount, Entity source) {
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
       this.maxHealth = this.defaultHealth;
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
