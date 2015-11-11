package mario.entity;

import mario.common.BlockType;
import mario.common.Hitbox;

public class Block extends Entity {
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
