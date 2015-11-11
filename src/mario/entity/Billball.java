package mario.entity;

import mario.common.Hitbox;

public class Billball extends Enemy {
	private static final Hitbox hitbox = new Hitbox(1, 1);
	
    public Billball() {
        this.currentHitbox = Billball.hitbox; 
    }
}
