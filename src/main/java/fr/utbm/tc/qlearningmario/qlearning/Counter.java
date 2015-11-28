package fr.utbm.tc.qlearningmario.qlearning;

/**
 * @param <T> Used to Count object
 */
class Counter<T extends Object> {
	private static int numberOfT = 0;
	private final int id;

	public Counter() {
		this.id = numberOfT;
		++numberOfT;
	}

	protected int getID() {
		return this.id;
	}
}
