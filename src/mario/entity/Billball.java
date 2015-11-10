package mario.entity;

import mario.common.Hitbox;

public class Billball extends Enemy {
    public Billball() {
        this.currentHitbox = new Hitbox(0, 0, 1, 1); 
    }
}
