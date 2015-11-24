package fr.utbm.tc.qlearningmario.mario.entity;

import fr.utbm.tc.qlearningmario.mario.common.Hitbox;

public class Coin extends Entity implements Collectable {
	private static final Hitbox hitbox = new Hitbox(1, 1);
	
    private boolean isCollected = false;
    private Entity collector = null;

    public Coin() {
        this.currentHitbox = Coin.hitbox; 
    }
    
    @Override
    public void collect(Entity entity) {
        this.isCollected = true;
        this.collector = entity;
    }
    
    @Override
    public boolean isCollected() {
        return this.isCollected;
    }
    
    @Override
    public Entity getCollector() {
        return this.collector;
    }

}
