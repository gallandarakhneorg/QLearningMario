package fr.utbm.tc.qlearningmario.mario.entity;

import fr.utbm.tc.qlearningmario.mario.common.BlockType;

public final class BrickBlock extends Block implements Damageable {
	private boolean isDestroyed = true;

	public BrickBlock() {
		super(BlockType.BrickBlock);
	}
	
	@Override
	public void damage(int amount) {
		this.kill();
	}

	@Override
	public void damage(int amount, Entity source) {
		this.kill();
	}

	@Override
	public void kill() {
		// FIXME: synchronized?
		this.isDestroyed = true;
	}
	
	public boolean isDestroyed() {
		return this.isDestroyed;
	}

	@Override
	public int getHealth() {
		return this.isDestroyed ? 0 : 1;
	}

	@Override
	public void setHealth(int health) {
		// health cannot be changed.
	}

	@Override
	public int getMaxHealth() {
		return 1;
	}

	@Override
	public void setMaxHealth(int maxHealth) {
		// Max health cannot be changed.
	}

	@Override
	public void resetMaxHealth() {
		// Reset max health cannot be changed nor reset.
	}

	@Override
	public void heal(int amount) {
		// A block cannot be healed.
	}

	@Override
	public boolean isInvincible() {
		return false;
	}

	@Override
	public double getNoDamageTimestamp() {
		return 0.f;
	}

	@Override
	public void setNoDamageTimestamp(double timestamp) {
		// A block doesn't have any noDamageTimestamp.
	}
}
