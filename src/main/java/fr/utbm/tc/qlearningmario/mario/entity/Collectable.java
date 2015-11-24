package fr.utbm.tc.qlearningmario.mario.entity;

public interface Collectable {
    public void collect(Entity entity);

    public boolean isCollected();

    public Entity getCollector();
}
