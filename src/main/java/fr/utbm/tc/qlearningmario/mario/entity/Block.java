package fr.utbm.tc.qlearningmario.mario.entity;

import fr.utbm.tc.qlearningmario.mario.common.BlockType;
import fr.utbm.tc.qlearningmario.mario.common.Hitbox;

public class Block extends Entity implements Solid {
	private static final Hitbox hitbox = new Hitbox(1, 1);
	
	private BlockType type;
	
	public Block(BlockType type) {
		this.type = type;
		this.currentHitbox = Block.hitbox;
	}
	
	public BlockType getType() {
		return this.type;
	}
}
