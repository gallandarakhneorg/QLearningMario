package fr.utbm.tc.qlearningmario.mario.entity;

public interface Damageable {
	public void damage(int amount);
	
	public void damage(int amount, Entity source);
	
	public void kill();
	
	public int getHealth();
	
	public void setHealth(int health);
	
	public int getMaxHealth();
	
	public void setMaxHealth(int maxHealth);
	
	public void resetMaxHealth();
	
	public void heal(int amount);
	
	public boolean isInvincible();

	public double getNoDamageTimestamp();
	
	public void setNoDamageTimestamp(double timestamp);
}
