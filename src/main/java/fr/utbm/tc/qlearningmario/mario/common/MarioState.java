package fr.utbm.tc.qlearningmario.mario.common;

public enum MarioState {
	DeadMario,
	SmallMario,
	SuperMario,
	FireMario;
	
	public static MarioState fromHealth(int health) {
		assert(health >= 0 && health < values().length);
		return values()[health];
	}
}
