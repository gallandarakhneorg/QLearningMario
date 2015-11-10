package mario.entity;

import mario.common.Hitbox;

public class PirahnaPlant extends Enemy {
    public PirahnaPlant() {
        this.currentHitbox = new Hitbox(0, 0, 1, 1.5); 
    }
}
