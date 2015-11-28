package fr.utbm.tc.qlearningmario.mario.common;

public enum MarioState {
	DEAD_MARIO,
	SMALL_MARIO,
	SUPER_MARIO,
	FIRE_MARIO;

	public static MarioState fromHealth(int health) {
		assert(health >= 0 && health < values().length);
		return values()[health];
	}
}
