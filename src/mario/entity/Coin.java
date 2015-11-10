package mario.entity;

public class Coin extends Entity implements Collectable {

    private boolean isCollected = false;
    private Entity collecter = null;

    @Override
    public void collect(Entity entity) {
        this.isCollected = true;
        this.collecter = entity;
    }
    
    @Override
    public boolean isCollected() {
        return this.isCollected;
    }
    
    @Override
    public Entity getCollecter() {
        return this.collecter;
    }

}
