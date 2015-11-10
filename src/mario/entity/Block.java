package mario.entity;

import mario.common.BlockType;
import mario.common.Hitbox;

public class Block extends Entity {
	private BlockType type;
	
	public Block(BlockType type) {
		this.type = type;
		this.currentHitbox = new Hitbox(0, 0, 1, 1);
	}
	
	public BlockType getType() {
		return this.type;
	}
}
