package mario.entity;

import mario.common.BlockType;

public class Block extends Entity {
	private BlockType type;
	
	public Block(BlockType type) {
		this.type = type;
	}
	
	public BlockType getType() {
		return this.type;
	}
}
