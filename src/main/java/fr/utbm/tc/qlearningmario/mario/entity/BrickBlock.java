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

import fr.utbm.tc.qlearningmario.mario.common.BlockType;

public final class BrickBlock extends Block<BrickBlock> implements Damageable {
	private boolean isDestroyed = true;

	public BrickBlock() {
		super(BlockType.BRICK_BLOCK);
	}

	@Override
	public void damage(int amount) {
		this.kill();
	}

	@Override
	public void damage(int amount, Entity<?> source) {
		this.kill();
	}

	@Override
	public void kill() {
		this.isDestroyed = true;
	}

	@Override
	public boolean isDead() {
		return this.isDestroyed;
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
