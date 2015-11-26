package fr.utbm.tc.qlearningmario.mario.entity;

import java.util.EventObject;

public class WorldEvent extends EventObject {

	public enum Type {
		WORLD_UPDATE,
		ENTITY_ADDED,
		ENTITY_REMOVED
	}
	
	private static final long serialVersionUID = 8762395640426505158L;
	
	private final Type eventType;
	
	private Entity<?> entity;
	
	public WorldEvent(World source, Type eventType) {
		super(source);
		this.eventType = eventType;
	}
	
	public WorldEvent(World source, Entity<?> entity, Type eventType) {
		this(source, eventType);
		this.entity = entity;
	}
	
	@Override
	public World getSource() {
		return (World) super.getSource();
	}

	public Entity<?> getEntity() {
		return this.entity;
	}
	
	public Type getType() {
		return this.eventType;
	}
}
