package mario.entity;

import mario.common.Hitbox;

public class PirahnaPlant extends Enemy {
	private static final Hitbox hitbox = new Hitbox(1, 1.5);
	
    public PirahnaPlant() {
        this.currentHitbox = PirahnaPlant.hitbox; 
    }
}
