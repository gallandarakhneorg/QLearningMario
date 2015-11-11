package mario.entity;

import mario.common.Hitbox;

public class Goomba extends Enemy {
	private static final Hitbox hitbox = new Hitbox(1, 1);
	
    public Goomba() {
        this.currentHitbox = Goomba.hitbox; 
    }
}
