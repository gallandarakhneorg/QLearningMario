package fr.utbm.tc.qlearningmario.mario.entity;

public interface Damageable {
	void damage(int amount);

	void damage(int amount, Entity<?> source);

	void kill();

	boolean isDead();

	int getHealth();

	void setHealth(int health);

	int getMaxHealth();

	void setMaxHealth(int maxHealth);

	void resetMaxHealth();

	void heal(int amount);

	boolean isInvincible();

	double getNoDamageTimestamp();

	void setNoDamageTimestamp(double timestamp);
}
