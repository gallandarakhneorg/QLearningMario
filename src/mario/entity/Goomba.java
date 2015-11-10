package mario.entity;

import mario.common.Hitbox;

public class Goomba extends Enemy {
    public Goomba() {
        this.currentHitbox = new Hitbox(0, 0, 1, 1); 
    }
}
