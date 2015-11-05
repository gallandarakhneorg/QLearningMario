package qlearning;

public class QState extends Counter<QState> implements QBase{

	private static final long serialVersionUID = -1506512375211501871L;

	@Override
	public int toInt() {
		return getID();
	}

}
